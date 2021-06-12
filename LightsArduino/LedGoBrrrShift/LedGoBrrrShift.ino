#include <FastLED.h>

#define LED_DATA_PIN 7
#define NUM_LEDS 40
#define NUM_COLORS 7

CRGB ledStrip[NUM_LEDS];

int color = 0;

void setup()
{
  FastLED.addLeds<WS2812, LED_DATA_PIN, GRB>(ledStrip, NUM_LEDS);
}

void loop()
{
  for (int i = 0; i <= NUM_LEDS-1; i++)
  {
    setRange(0, i, getColor(7*i));
    FastLED.show();
    delay(40);
  }
  nextColor();
  for (int i = NUM_LEDS-1; i >= 0; i--)
  {
    setRange(i, NUM_LEDS-1, getColor(255-(7*i)));
    FastLED.show();
    delay(40);
  }
  nextColor();
}

void setRange(int startIndex, int endIndex, CRGB color)
{
  for (int i = 0; i < endIndex - startIndex; i++)
  {
    ledStrip[i + startIndex] = color;
  }
}

CRGB getColor(int shift)
{
  int val = 255-shift;
  switch (color)
  {
    case 0:
      return CRGB(val, 0, 0);
    case 1:
      return CRGB(0, val, 0);
    case 2:
      return CRGB(val, val, 0);
    case 3:
      return CRGB(0, 0, val);
    case 4:
      return CRGB(val, 0, val);
    case 5:
      return CRGB(0, val, val);
    case 6:
      return CRGB(val, val, val);
  }
  return CRGB(0, 0, 0);
}

void nextColor()
{
  if (color + 1 == NUM_COLORS)
    color = 0;
  else
    color++;
}
