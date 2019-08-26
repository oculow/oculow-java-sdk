package com.oculow;

/**
 *  Changes the logic use for the comparison stage. This can vary from detecting pixel by pixel differences between
 *  the baseline and the new image to ignoring any differences and only detecting errors.
 */
public enum COMPARISON {
    /**
     * Will calculate the exact differences between the baseline and the new image.
     * If a pixel is different, then the test will fail and the difference can be visualized in the dashboard.
     */
    PIXEL_DIFF,
    /**
     * Will compare the base image and the new image, ignoring slight differences that a human cannot perceive.
     * If there are perceptual differences in the image, the results can be visualized in the dashboard.
     */
    IGNORE_AA,
    /**
     * WARNING: This feature is still under development and may not behave as expected.
     * Will detect the shape of all the elements, ignoring AA effects.This way, only the actual content of
     * the image is compared and not the colors.
     */
    IGNORE_COLOR,
    /**
     * This will only detect errors in each website, ignoring the image comparison.
     * If any errors are detected, they can be visualized in the dashboard.
     * We will be constantly updating our error detection AI, so the behavior may change through time.
     */
    DETECT_ERRORS
}


