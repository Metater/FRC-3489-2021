#include <FastLED.h>
#define LED_PIN     7
#define NUM_LEDS    65
#define NUM_COLORS  7

CRGB leds[NUM_LEDS];

int color = 0;

void setup() {
  FastLED.addLeds<WS2812, LED_PIN, GRB>(leds, NUM_LEDS);
}
void loop() {
  /*
  for (int i = 0; i <= NUM_LEDS; i++)
  {
    leds[i] = CRGB((i*16)-1, (i*16)-1, (i*16)-1);
    FastLED.show();
  }
  delay(40);
  shiftArray();
  */

/*
  for (int i = 0; i <= NUM_LEDS; i++)
  {
    int a = (i*4)-1;
    if (a == -1) a = 0;
    leds[i] = CRGB(a, a, a);
    FastLED.show();
  }
  */

  old();
  
}

void old()
{
    for (int i = 0; i <= NUM_LEDS-1; i++) {
    if (i % 2 == 0)
      leds[i] = getColor(225);
    else
      leds[i] = getColor(0);
    FastLED.show();
    delay(40);
  }
  nextColor();
  for (int i = NUM_LEDS-1; i >= 0; i--) {
    if (i % 2 == 0)
      leds[i] = getColor(225);
    else
      leds[i] = getColor(0);
    FastLED.show();
    delay(40);
  }
  nextColor();
}

void shiftArray()
{
  for (int i = NUM_LEDS-1; i > 0; i--)
  {
    leds[i] = leds[i-1];
    FastLED.show();
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
  if (color+1 == NUM_COLORS)
  {
    color = 0;
  }
  else
  {
    color++;
  }
}
