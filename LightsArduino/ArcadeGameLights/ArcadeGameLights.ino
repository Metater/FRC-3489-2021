#include <FastLED.h>

#define LED_DATA_PIN 3
#define NUM_LEDS 162
#define NUM_COLORS  7

CRGB ledStrip[NUM_LEDS];

bool wipeRan = false;

int color = 0;

void setup()
{
  FastLED.addLeds<WS2812, LED_DATA_PIN, GRB>(ledStrip, NUM_LEDS);
  FastLED.setBrightness(50);
}

void loop()
{
  //bounceColorChange(20);
  //CRGB colors[] = { CRGB(6, 89, 20), CRGB(20, 153, 43), CRGB(0, 255, 0) };
  //CRGB colors[] = { CRGB(255, 0, 0), CRGB(255, 165, 0), CRGB(255, 255, 0), CRGB(0, 128, 0), CRGB(0, 0, 255), CRGB(75, 0, 130), CRGB(238, 130, 238) };
  //titaniumWave(colors, 7, 100);
  CRGB colors[] = { CRGB(255, 0, 0), CRGB(173, 0, 0), CRGB(102, 0, 0), CRGB(60, 0, 60), CRGB(77, 0, 135), CRGB(145, 0, 255), CRGB(77, 0, 135), CRGB(60, 0, 60), CRGB(0, 0, 102), CRGB(0, 0, 173), CRGB(0, 0, 255) };
  titaniumWave(colors, 11, 250);
}

void titanium(CRGB colors[], int l, int cycleDelay)
{
  for (int i = 0; i < 7; i++)
  {
    setStrip(colors[i]);
    FastLED.show();
    delay(cycleDelay); 
  }
}

void titaniumWave(CRGB colors[], int l, int cycleDelay)
{
  for (int i = 0; i < l; i++)
  {
    setStrip(colors[i]);
    FastLED.show();
    delay(cycleDelay);
  }
  for (int i = l - 1; i >= 0; i--)  
  {
    setStrip(colors[i]);
    FastLED.show();
    delay(cycleDelay);
  }
}

void wipeColor()
{
  if (!wipeRan)
  {
    wipeRan = true;
    setRange(0, NUM_LEDS-1, CRGB(0, 255, 0));
  }
  shiftStripRight(1);
  FastLED.show();
  delay(1);
}

void bounceColorChange(int cycleDelay)
{
  for (int i = 0; i <= NUM_LEDS-1; i++)
  {
    if (i % 2 == 0) ledStrip[i] = getColor(225);
    else ledStrip[i] = getColor(0);
    FastLED.show();
    delay(cycleDelay);
  }
  nextColor();
  for (int i = NUM_LEDS-1; i >= 0; i--)
  {
    if (i % 2 == 0) ledStrip[i] = getColor(225);
    else ledStrip[i] = getColor(0);
    FastLED.show();
    delay(cycleDelay);
  }
  nextColor();
}

void nextColor()
{
  if (color+1 == NUM_COLORS) color = 0;
  else color++;
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
void setStrip(CRGB color)
{
  setRange(0, NUM_LEDS-1, color);
  FastLED.show();
}
