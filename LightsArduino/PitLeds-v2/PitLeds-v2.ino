#include <FastLED.h>

#define LED_DATA_PIN 7
#define NUM_LEDS 416

CRGB ledStrip[NUM_LEDS];

void setup()
{
  FastLED.addLeds<WS2812, LED_DATA_PIN, GRB>(ledStrip, NUM_LEDS);
  FastLED.setBrightness(60);
}

void loop()
{
  for (int i = 0; i < 208; i++)
  {
    for (int j = 0; j < NUM_LEDS; j++) ledStrip[j] = CRGB (0, 0, 0);
    
    for (int j = 0; j < 4; j++)
    {
      setLed(-i, 207 - j, CRGB(255, 0, 0), false);
      setLed(-i, 203 - j, CRGB(255, 153, 0), false);
      setLed(-i, 199 - j, CRGB(255, 255, 0), false);
      setLed(-i, 195 - j, CRGB(9, 255, 0), false);
      setLed(-i, 191 - j, CRGB(0, 8, 255), false);
      setLed(-i, 187 - j, CRGB(93, 0, 255), false);
      setLed(-i, 183 - j, CRGB(210, 114, 242), false);
      
      setLed(-i, 179 - j, CRGB(210, 114, 242), false);
      setLed(-i, 175 - j, CRGB(93, 0, 255), false);
      setLed(-i, 171 - j, CRGB(0, 8, 255), false);
      setLed(-i, 167 - j, CRGB(9, 255, 0), false);
      setLed(-i, 163 - j, CRGB(255, 255, 0), false);
      setLed(-i, 159 - j, CRGB(255, 153, 0), false);
      setLed(-i, 155 - j, CRGB(255, 0, 0), false);

      setLed(-i, 151 - j, CRGB(255, 0, 0), false);
      setLed(-i, 147 - j, CRGB(255, 153, 0), false);
      setLed(-i, 143 - j, CRGB(255, 255, 0), false);
      setLed(-i, 139 - j, CRGB(9, 255, 0), false);
      setLed(-i, 135 - j, CRGB(0, 8, 255), false);
      setLed(-i, 131 - j, CRGB(93, 0, 255), false);
      setLed(-i, 127 - j, CRGB(210, 114, 242), false);
      
      setLed(-i, 123 - j, CRGB(210, 114, 242), false);
      setLed(-i, 119 - j, CRGB(93, 0, 255), false);
      setLed(-i, 115 - j, CRGB(0, 8, 255), false);
      setLed(-i, 111 - j, CRGB(9, 255, 0), false);
      setLed(-i, 107 - j, CRGB(255, 255, 0), false);
      setLed(-i, 103 - j, CRGB(255, 153, 0), false);
      setLed(-i, 99 - j, CRGB(255, 0, 0), false);


      
      setLed(i, 208  + j, CRGB(255, 0, 0), true);
      setLed(i, 212  + j, CRGB(255, 153, 0), true);
      setLed(i, 216  + j, CRGB(255, 255, 0), true);
      setLed(i, 220  + j, CRGB(9, 255, 0), true);
      setLed(i, 224  + j, CRGB(0, 8, 255), true);
      setLed(i, 228 + j, CRGB(93, 0, 255), true);
      setLed(i, 232 + j, CRGB(210, 114, 242), true);

      setLed(i, 236  + j, CRGB(210, 114, 242), true);
      setLed(i, 240  + j, CRGB(93, 0, 255), true);
      setLed(i, 244  + j, CRGB(0, 8, 255), true);
      setLed(i, 248  + j, CRGB(9, 255, 0), true);
      setLed(i, 252  + j, CRGB(255, 255, 0), true);
      setLed(i, 256 + j, CRGB(255, 153, 0), true);
      setLed(i, 260 + j, CRGB(255, 0, 0), true);

      setLed(i, 264  + j, CRGB(255, 0, 0), true);
      setLed(i, 268  + j, CRGB(255, 153, 0), true);
      setLed(i, 272  + j, CRGB(255, 255, 0), true);
      setLed(i, 276  + j, CRGB(9, 255, 0), true);
      setLed(i, 280  + j, CRGB(0, 8, 255), true);
      setLed(i, 284 + j, CRGB(93, 0, 255), true);
      setLed(i, 288 + j, CRGB(210, 114, 242), true);

      setLed(i, 292  + j, CRGB(210, 114, 242), true);
      setLed(i, 296  + j, CRGB(93, 0, 255), true);
      setLed(i, 300  + j, CRGB(0, 8, 255), true);
      setLed(i, 304  + j, CRGB(9, 255, 0), true);
      setLed(i, 308  + j, CRGB(255, 255, 0), true);
      setLed(i, 312 + j, CRGB(255, 153, 0), true);
      setLed(i, 316 + j, CRGB(255, 0, 0), true);
    }
    
    FastLED.show();
    delay(1);
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
