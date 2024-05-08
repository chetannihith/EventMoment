package com.example.eventmoment.features.eventCreation.databinding;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.eventmoment.features.eventCreation.R;


public final class ActivitySignInBinding implements ViewBinding {
    public final ImageView buttonIcon;
    public final ProgressBar buttonLoader;
    public final TextView buttonText;
    public final LinearLayout rlLoginGG;
    public final LinearLayout rlOtherSignIn;
    public final RelativeLayout rlEventMoment;
    private final LinearLayout rootView;

    private ActivitySignInBinding(LinearLayout linearLayout, ImageView imageView, ProgressBar progressBar, TextView textView, LinearLayout linearLayout2, LinearLayout linearLayout3, RelativeLayout relativeLayout) {
        this.rootView = linearLayout;
        this.buttonIcon = imageView;
        this.buttonLoader = progressBar;
        this.buttonText = textView;
        this.rlLoginGG = linearLayout2;
        this.rlOtherSignIn = linearLayout3;
        this.rlEventMoment = relativeLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivitySignInBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivitySignInBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.activity_sign_in, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ActivitySignInBinding bind(View view) {
        int i = R.id.buttonIcon;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i);
        if (imageView != null) {
            i = R.id.buttonLoader;
            ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(view, i);
            if (progressBar != null) {
                i = R.id.buttonText;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i);
                if (textView != null) {
                    i = R.id.rlLoginGG;
                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i);
                    if (linearLayout != null) {
                        i = R.id.rlOtherSignIn;
                        LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(view, i);
                        if (linearLayout2 != null) {
                            i = R.id.rlEventMoment;
                            RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, i);
                            if (relativeLayout != null) {
                                return new ActivitySignInBinding((LinearLayout) view, imageView, progressBar, textView, linearLayout, linearLayout2, relativeLayout);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}