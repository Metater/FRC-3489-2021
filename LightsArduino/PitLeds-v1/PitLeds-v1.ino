#include <FastLED.h>

#define LED_DATA_PIN 7
#define NUM_LEDS 40

CRGB ledStrip[NUM_LEDS];

void setup()
{
  FastLED.addLeds<WS2812, LED_DATA_PIN, GRB>(ledStrip, NUM_LEDS);
}

void loop()
{
  for (int i = 0; i < 20; i++)
  {
    CRGB strip[40];
    strip[19-i] = CRGB (255, 0, 0);
    strip[20+i] = CRGB (255, 0, 0);
    for (int i = 0; i < 40; i++) ledStrip[i] = strip[i];
    FastLED.show();
    delay(50);
  }
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
void setRange(int startIndex, int endIndex, CRGB color)
{
  for (int i = 0; i < endIndex - startIndex; i++)
  {
    ledStrip[i + startIndex] = color;
  }
}
