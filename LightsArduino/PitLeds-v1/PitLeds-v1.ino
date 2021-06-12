#include <FastLED.h>

#define LED_DATA_PIN 7
#define NUM_LEDS 162

CRGB ledStrip[NUM_LEDS];

void setup()
{
  FastLED.addLeds<WS2812, LED_DATA_PIN, GRB>(ledStrip, NUM_LEDS);
  FastLED.setBrightness(30);
}

void loop()
{
  for (int i = 0; i < 81; i++)
  {
    for (int j = 0; j < 162; j++) ledStrip[j] = CRGB (0, 0, 0);
    
    for (int j = 0; j < 4; j++)
    {
      setLed(-i, 80 - j, CRGB(255, 0, 0), false);
      setLed(-i, 76 - j, CRGB(255, 153, 0), false);
      setLed(-i, 72 - j, CRGB(255, 255, 0), false);
      setLed(-i, 68 - j, CRGB(9, 255, 0), false);
      setLed(-i, 64 - j, CRGB(0, 8, 255), false);
      setLed(-i, 60 - j, CRGB(93, 0, 255), false);
      setLed(-i, 56 - j, CRGB(210, 114, 242), false);
      
      setLed(-i, 52 - j, CRGB(210, 114, 242), false);
      setLed(-i, 48 - j, CRGB(93, 0, 255), false);
      setLed(-i, 44 - j, CRGB(0, 8, 255), false);
      setLed(-i, 40 - j, CRGB(9, 255, 0), false);
      setLed(-i, 36 - j, CRGB(255, 255, 0), false);
      setLed(-i, 32 - j, CRGB(255, 153, 0), false);
      setLed(-i, 28 - j, CRGB(255, 0, 0), false);


      
      setLed(i, 81  + j, CRGB(255, 0, 0), true);
      setLed(i, 85  + j, CRGB(255, 153, 0), true);
      setLed(i, 89  + j, CRGB(255, 255, 0), true);
      setLed(i, 93  + j, CRGB(9, 255, 0), true);
      setLed(i, 97  + j, CRGB(0, 8, 255), true);
      setLed(i, 101 + j, CRGB(93, 0, 255), true);
      setLed(i, 105 + j, CRGB(210, 114, 242), true);

      setLed(i, 109  + j, CRGB(210, 114, 242), true);
      setLed(i, 113  + j, CRGB(93, 0, 255), true);
      setLed(i, 117 + j, CRGB(0, 8, 255), true);
      setLed(i, 121 + j, CRGB(9, 255, 0), true);
      setLed(i, 125 + j, CRGB(255, 255, 0), true);
      setLed(i, 129 + j, CRGB(255, 153, 0), true);
      setLed(i, 133 + j, CRGB(255, 0, 0), true);
    }
    
    FastLED.show();
    delay(15);
  }
}

void setLed(int i, int offset, CRGB color, bool right)
{
  right = true;
  if (right)
  {
    if (i + offset < 0 && i + offset > NUM_LEDS) return;
    ledStrip[i + offset] = color;
  }
  else
  {
    if (i - offset < 0 && i - offset > NUM_LEDS) return;
    ledStrip[i - offset] = color;
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
