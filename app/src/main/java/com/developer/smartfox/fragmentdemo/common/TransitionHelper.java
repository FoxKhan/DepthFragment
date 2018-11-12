//package com.developer.smartfox.fragmentdemo.common;
//
//import android.animation.ObjectAnimator;
//import android.view.View;
//import android.view.animation.Interpolator;
//import com.example.pc.depthfox.R;
//import no.agens.depth.lib.DepthLayout;
//import no.agens.depth.lib.tween.interpolators.*;
//
//public class TransitionHelper {
//
//    private static final float TARGET_SCALE = 0.5f;
//    private static final float TARGET_ROTATION = -50f;
//    private static final float TARGET_ROTATION_X = 60f;
//    private static final int MOVE_Y_STEP = 15;
//    private static final int DURATION = 1100;
//    private static final QuintOut VALUE_INTERPOLATOR = new QuintOut();
//    private static final Interpolator INTERPOLATOR = new CustomInterpolator();
//
//    private static final int FIRST_DELAY = 300;
//
//    public static void startExitAnim(View root) {
//        exitAnimate((DepthLayout) root.findViewById(R.id.container_bottom_item), 0, 30f, 15, 190, true);
//        exitAnimate((DepthLayout) root.findViewById(R.id.container_middle_item), MOVE_Y_STEP, 20f, 30, 170, true);
//        exitAnimate((DepthLayout) root.findViewById(R.id.container_upper_item), MOVE_Y_STEP * 2f, 20f, 45, 210, true);
//    }
//
//
//    private static void exitAnimate(final DepthLayout target, final float moveY, final float customElevation, long delay, int subtractDelay, boolean continueOffscreen) {
//
//        target.setPivotY(getDistanceToCenterY(target));
//        target.setPivotX(getDistanceToCenterX(target));
//        target.setCameraDistance(10000 * target.getResources().getDisplayMetrics().density);
//
//        ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, TARGET_ROTATION_X).setDuration(DURATION);
//        rotationX.setInterpolator(VALUE_INTERPOLATOR);
//        rotationX.setStartDelay(delay);
//        rotationX.start();
//
//        ObjectAnimator elevation = ObjectAnimator.ofFloat(target, "CustomShadowElevation", target.getCustomShadowElevation(), customElevation * target.getResources().getDisplayMetrics().density).setDuration(DURATION);
//        elevation.setInterpolator(VALUE_INTERPOLATOR);
//        elevation.setStartDelay(delay);
//        elevation.start();
//
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, TARGET_SCALE).setDuration(DURATION);
//        scaleX.setInterpolator(new QuintOut());
//        scaleX.setStartDelay(delay);
//        scaleX.start();
//
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, TARGET_SCALE).setDuration(DURATION);
//        scaleY.setInterpolator(new QuintOut());
//        scaleY.setStartDelay(delay);
//        scaleY.start();
//
//        ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, TARGET_ROTATION).setDuration(1600);
//        rotation.setInterpolator(VALUE_INTERPOLATOR);
//        rotation.setStartDelay(delay);
//        rotation.start();
//
//        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, -moveY * target.getResources().getDisplayMetrics().density).setDuration(subtractDelay);
//        translationY.setInterpolator(VALUE_INTERPOLATOR);
//        translationY.setStartDelay(delay);
//        translationY.start();
//
//        if (continueOffscreen) continueOutToRight(target, moveY, subtractDelay);
//    }
//
//    private static void continueOutToRight(DepthLayout target, float moveY, int subtractDelay) {
//        ObjectAnimator translationY2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, -moveY * target.getResources().getDisplayMetrics().density, -target.getResources().getDisplayMetrics().heightPixels).setDuration(900);
//        translationY2.setInterpolator(new ExpoIn());
//        translationY2.setStartDelay(subtractDelay);
//
//        translationY2.start();
//
//        ObjectAnimator translationX2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, target.getTranslationX(), target.getResources().getDisplayMetrics().widthPixels).setDuration(900);
//        translationX2.setInterpolator(new ExpoIn());
//        translationX2.setStartDelay(subtractDelay);
//        translationX2.start();
//    }
//
//    private static float getDistanceToCenterY(View target) {
//        float viewCenter = target.getTop() + target.getHeight() / 2f;
//        float rootCenter = ((View) target.getParent()).getHeight() / 2;
//        return target.getHeight() / 2f + rootCenter - viewCenter;
//    }
//
//    private static float getDistanceToCenterX(View target) {
//        float viewCenter = target.getLeft() + target.getWidth() / 2f;
//        float rootCenter = ((View) target.getParent()).getWidth() / 2;
//        return target.getWidth() / 2f + rootCenter - viewCenter;
//    }
//
//
//    public static void startIntroAnim(View root) {
//        introAnimate((DepthLayout) root.findViewById(R.id.container_bottom_item), 0, 30f, 15, 190);
//        introAnimate((DepthLayout) root.findViewById(R.id.container_middle_item), MOVE_Y_STEP, 20f, 30, 170);
//        introAnimate((DepthLayout) root.findViewById(R.id.container_upper_item), MOVE_Y_STEP * 2f, 20f, 45, 210);
//    }
//
//    private static void introAnimate(final DepthLayout target, final float moveY, final float customElevation, long delay, int subtractDelay) {
//
//        target.setPivotY(getDistanceToCenterY(target));
//        target.setPivotX(getDistanceToCenterX(target));
//        target.setCameraDistance(10000 * target.getResources().getDisplayMetrics().density);
//
//        ObjectAnimator translationY2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, target.getResources().getDisplayMetrics().heightPixels, -moveY * target.getResources().getDisplayMetrics().density).setDuration(800);
//        translationY2.setInterpolator(new ExpoOut());
//        translationY2.setStartDelay(700 + subtractDelay);
//        translationY2.start();
//        target.setTranslationY(target.getResources().getDisplayMetrics().heightPixels);
//
//        ObjectAnimator translationX2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, -target.getResources().getDisplayMetrics().widthPixels, 0).setDuration(800);
//        translationX2.setInterpolator(new ExpoOut());
//        translationX2.setStartDelay(700 + subtractDelay);
//        translationX2.start();
//        target.setTranslationX(-target.getResources().getDisplayMetrics().widthPixels);
//
//        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, 0).setDuration(700);
//        translationY.setInterpolator(new BackOut());
//        translationY.setStartDelay(700 + 800);
//        translationY.start();
//
//
//        ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, TARGET_ROTATION_X, 0).setDuration(1000);
//        rotationX.setInterpolator(new QuintInOut());
//        rotationX.setStartDelay(700 + FIRST_DELAY + subtractDelay);
//        rotationX.start();
//        target.setRotationX(TARGET_ROTATION_X);
//
//        ObjectAnimator elevation = ObjectAnimator.ofFloat(target, "CustomShadowElevation", customElevation * target.getResources().getDisplayMetrics().density, target.getCustomShadowElevation()).setDuration(1000);
//        elevation.setInterpolator(new QuintInOut());
//        elevation.setStartDelay(700 + FIRST_DELAY + subtractDelay * 2);
//        elevation.start();
//        target.setCustomShadowElevation(customElevation * target.getResources().getDisplayMetrics().density);
//
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, TARGET_SCALE, target.getScaleX()).setDuration(1000);
//        scaleX.setInterpolator(new CircInOut());
//        scaleX.setStartDelay(700 + FIRST_DELAY + subtractDelay);
//        scaleX.start();
//        target.setScaleX(TARGET_SCALE);
//
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, TARGET_SCALE, target.getScaleY()).setDuration(1000);
//        scaleY.setInterpolator(new CircInOut());
//        scaleY.setStartDelay(700 + FIRST_DELAY + subtractDelay);
//        scaleY.start();
//        target.setScaleY(TARGET_SCALE);
//
//        ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, TARGET_ROTATION, 0).setDuration(1400);
//        rotation.setInterpolator(new QuadInOut());
//        rotation.setStartDelay(FIRST_DELAY + subtractDelay);
//        rotation.start();
//        target.setRotation(TARGET_ROTATION);
////        rotation.addListener(getShowStatusBarListener(target));
//    }
//
//
//    public static void animateToMenuState(View root) {
//        exitAnimate((DepthLayout) root.findViewById(R.id.container_bottom_item), 0, 30f, 15, 190, false);
//        exitAnimate((DepthLayout) root.findViewById(R.id.container_middle_item), MOVE_Y_STEP, 20f, 30, 170, false);
//        exitAnimate((DepthLayout) root.findViewById(R.id.container_upper_item), MOVE_Y_STEP * 2f, 20f, 45, 210, false);
//
//        ObjectAnimator translationY = ObjectAnimator.ofFloat(root, View.TRANSLATION_Y, -90f * root.getResources().getDisplayMetrics().density).setDuration(DURATION);
//        translationY.setInterpolator(VALUE_INTERPOLATOR);
//        translationY.start();
//    }
//
//    private static void makeAppFullscreen(View target) {
////        ((Activity) target.getContext()).getWindow().setStatusBarColor(Color.TRANSPARENT);
////        ((Activity) target.getContext()).getWindow().getDecorView().setSystemUiVisibility(
////                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
////                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//    }
//
//    public static void animateMenuOut(View root) {
//        continueOutToRight((DepthLayout) root.findViewById(R.id.container_bottom_item), 0, 20);
//        continueOutToRight((DepthLayout) root.findViewById(R.id.container_middle_item), MOVE_Y_STEP, 0);
//        continueOutToRight((DepthLayout) root.findViewById(R.id.container_upper_item), MOVE_Y_STEP * 2f, 40);
//    }
//
//    public static void startRevertFromMenu(View root) {
//
//        revertFromMenu((DepthLayout) root.findViewById(R.id.container_bottom_item), 30f, 10, 0);
//        revertFromMenu((DepthLayout) root.findViewById(R.id.container_middle_item), 20f, 0, 0);
//        revertFromMenu((DepthLayout) root.findViewById(R.id.container_upper_item), 20f, 20, 6);
//        ObjectAnimator translationY = ObjectAnimator.ofFloat(root, View.TRANSLATION_Y, 0).setDuration(DURATION);
//        translationY.setInterpolator(new QuintInOut());
//        translationY.start();
//    }
//
//
//    private static void revertFromMenu(final DepthLayout target, final float customElevation, int subtractDelay, float targetElevation) {
//
//        target.setPivotY(getDistanceToCenterY(target));
//        target.setPivotX(getDistanceToCenterX(target));
//        target.setCameraDistance(10000 * target.getResources().getDisplayMetrics().density);
//
//
//        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, 0).setDuration(700);
//        translationY.setInterpolator(new BackOut());
//        translationY.setStartDelay(250 + FIRST_DELAY + subtractDelay);
//        translationY.start();
//
//
//        ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, target.getRotationX(), 0).setDuration(1000);
//        rotationX.setInterpolator(new QuintInOut());
//        rotationX.setStartDelay(FIRST_DELAY + subtractDelay);
//        rotationX.start();
//        target.setRotationX(TARGET_ROTATION_X);
//
//
//        ObjectAnimator elevation = ObjectAnimator.ofFloat(target, "CustomShadowElevation",
//                target.getCustomShadowElevation(),
//                targetElevation * target.getResources().getDisplayMetrics().density)
//                .setDuration(1000);
//
//        elevation.setInterpolator(new QuintInOut());
//        elevation.setStartDelay(FIRST_DELAY + subtractDelay * 2);
//        elevation.start();
//        target.setCustomShadowElevation(customElevation * target.getResources().getDisplayMetrics().density);
//
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, target.getScaleX(), 1f).setDuration(1000);
//        scaleX.setInterpolator(new CircInOut());
//        scaleX.setStartDelay(FIRST_DELAY + subtractDelay);
//        scaleX.start();
//        target.setScaleX(TARGET_SCALE);
//
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, target.getScaleY(), 1f).setDuration(1000);
//        scaleY.setInterpolator(new CircInOut());
//        scaleY.setStartDelay(FIRST_DELAY + subtractDelay);
//        scaleY.start();
//        target.setScaleY(TARGET_SCALE);
//
//        ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, target.getRotation(), 0).setDuration(1100);
//        rotation.setInterpolator(new QuintInOut());
//        rotation.setStartDelay(subtractDelay);
//        rotation.start();
////        rotation.addListener(getShowStatusBarListener(target));
//    }
//
//    //    @NonNull
////    private static AnimatorListenerAdapter getShowStatusBarListener(final DepthLayout target) {
////        return new AnimatorListenerAdapter() {
////            @Override
////            public void onAnimationEnd(Animator animation) {
////                super.onAnimationEnd(animation);
////                makeAppFullscreen(target);
////            }
////        };
////    }
//}
