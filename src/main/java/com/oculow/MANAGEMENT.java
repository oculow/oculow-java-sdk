package com.oculow;

/**
 * The baseline management system will define the action to be taken when a new image is detected,
 * will it add it as a new baseline? or raise an exception and let you know of a new image? With this setting, you
 * will let us know what action to take. There are 4 types of logic that can be used.
 * Each one can be set by referencing the library and indicating the level.
 */
public enum MANAGEMENT {
    /**
     *Each new image will cause the test to fail, indicating that there is a new baseline to be set. You will have to
     * manually accept each baseline.
     */
    MANUAL,
    /**
     * We will apply our A.I. technologies to detect if any visual errores exist in the image.
     * If none are detected, the image is set as baseline. Otherwise, a prompt will be sent to the test, causing it to
     * fail and ask for manual input.
     */
    ASSISTED,
    /**
     * Any new baselines detected will be forced as baselines. Any existing images will be compared and if
     * differences exist, it will fail the test.
     */
    FORCE_NEW,
    /**
     * All images will be forced as baselines. If the comparison fails, it will set the new image as a baseline.
     * Can be useful when major app changes occur, in order to update all baselines.
     */
    FORCE_ALL
}
