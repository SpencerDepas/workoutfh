package com.compscieddy.workoutfh;

import android.os.Bundle;
import android.view.View;

import com.compscieddy.eddie_utils.etil.Etil;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

abstract class FloatingBaseFragment extends DialogFragment {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NORMAL, R.style.FloatingFullscreenDialogTheme);
  }

  @Override
  public void onStart() {
    super.onStart();
    getDialog().getWindow().setLayout(MATCH_PARENT, MATCH_PARENT);

    runEnteringAnimation();
  }

  @Override
  public void onResume() {
    super.onResume();
    attachListeners();
  }

  @Override
  public void onPause() {
    super.onPause();
    detachListeners();
  }

  abstract View getBlackBackground();

  abstract View getMainDialogContainer();

  private void attachListeners() {
    getBlackBackground().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismissWithAnimation();
      }
    });
  }

  private void detachListeners() {
    getBlackBackground().setOnClickListener(null);
  }

  protected void dismissWithAnimation() {
    getBlackBackground().animate()
        .alpha(0)
        .setDuration(800)
        .withEndAction(new Runnable() {
          @Override
          public void run() {
            FloatingBaseFragment.this.dismiss();
          }
        });
    getMainDialogContainer().animate()
        .alpha(0)
        .setDuration(700)
        .setInterpolator(new FastOutSlowInInterpolator())
        .translationY(Etil.dpToPx(-40));
  }

  private void runEnteringAnimation() {
    getBlackBackground().setAlpha(0);
    getMainDialogContainer().setAlpha(0);

    getBlackBackground().animate()
        .alpha(1)
        .setDuration(300);
    getMainDialogContainer().animate()
        .alpha(1)
        .translationY(0)
        .setDuration(200);
  }
}
