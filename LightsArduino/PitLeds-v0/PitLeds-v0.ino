#include <FastLED.h>

#define LED_DATA_PIN 7
#define NUM_LEDS 40

CRGB ledStrip[NUM_LEDS];

int colorCounter = 0;

void setup()
{
  FastLED.addLeds<WS2812, LED_DATA_PIN, GRB>(ledStrip, NUM_LEDS);
}

void loop()
{
  shiftStripRight(1);
  if (colorCounter % 2 == 0)
    ledStrip[0] = CRGB (25, 0, 214);
  else
    ledStrip[1] = CRGB (168, 50, 54);
  FastLED.show();
  colorCounter++;
  delay(50);
}

void shiftStripLeft(int n)
{
  for (; n > 0; n--)
  {
    for (int i = 0; i < NUM_LEDS - 1; i++)
      ledStrip[i] = ledStrip[i+1];
    ledStrip[NUM_LEDS - 1] = CRGB (0, 0, 0); 
  }
}
void shiftStripRight(int n)
{
  for (; n > 0; n--)
  {
    for (int i = NUM_LEDS - 1; i > 0; i--)
      ledStrip[i] = ledStrip[i-1];
    ledStrip[0] = CRGB (0, 0, 0);
  }
}
void rotateStripLeft(int n)
{
  for (; n > 0; n--)
  {
    CRGB first = ledStrip[0];
    for (int i = 0; i < NUM_LEDS - 1; i++)
      ledStrip[i] = ledStrip[i+1];
    ledStrip[NUM_LEDS - 1] = first;
  }
}
void rotateStripRight(int n)
{
  for (; n > 0; n--)
  {
    CRGB last = ledStrip[NUM_LEDS - 1];
    for (int i = NUM_LEDS - 1; i > 0; i--)
      ledStrip[i] = ledStrip[i-1];
    ledStrip[0] = last;
  }
}
