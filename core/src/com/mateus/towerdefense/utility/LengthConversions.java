package com.mateus.towerdefense.utility;

class LengthConversions {
        public static final double SI_PIXEL = 0.0333;

        public static float Pixel2SIf(float pixels) {
         return (float) (pixels * SI_PIXEL);
        }

        public static float SI2Pixelf(float si) {
            return (float) (si/SI_PIXEL);
        }
}
