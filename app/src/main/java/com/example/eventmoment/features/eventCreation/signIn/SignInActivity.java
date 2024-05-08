package com.example.eventmoment.features.eventCreation.signIn;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.example.eventmoment.R;
import com.example.eventmoment.databinding.ActivitySignInBinding;
import com.example.eventmoment.features.eventCreation.home.HomeActivity;
import com.example.eventmoment.features.eventCreation.profile.ProfileActivity;
import com.example.eventmoment.navigation.navigators.WebViewNavigation;
import com.example.eventmoment.navigation.routes.URLRoutes;
import com.example.eventmoment.shared.ext.types.StringExtKt;
import com.example.eventmoment.shared.logs.LogsKt;
import com.example.eventmoment.shared_ui.arch.StatefulActivity;
import com.example.eventmoment.shared_ui.components.bottomsheet.SignInBottomSheet;
import com.example.eventmoment.shared_ui.components.dialog.EmailAuthDialog;
import com.example.eventmoment.shared_ui.components.toast.POVToastKt;
import com.example.eventmoment.shared_ui.delegate.viewBinding.ActivityViewBindingDelegate;
import com.example.eventmoment.shared_ui.ext.views.ViewExtKt;

public final class SignInActivity extends StatefulActivity<SignInState, SignInEffects> {
    private final ActivityViewBindingDelegate binding;
    private String intentURL;
    private boolean keepSplashOn;
    private final SignInBottomSheet signInBottomSheet;
    private SplashScreen splashScreen;
    private final SignInViewModel viewModel;
    private final WebViewNavigation webViewNavigation;

    public SignInActivity() {
        this.binding = new ActivityViewBindingDelegate(this, SignInActivity$binding$2.INSTANCE);
        this.signInBottomSheet = new SignInBottomSheet(this);
        this.viewModel = new SignInViewModel(); // Initialize your ViewModel here
        this.webViewNavigation = new WebViewNavigation();
        this.keepSplashOn = true;
    }

    @Override
    protected StatefulViewModel<SignInState, SignInEffects> getViewModel2() {
        return viewModel;
    }

    private final WebViewNavigation getWebViewNavigation() {
        return webViewNavigation;
    }

    private final ActivitySignInBinding getBinding() {
        return (ActivitySignInBinding) this.binding.getValue2((AppCompatActivity) this, SignInActivity$$delegatedProperties[0]);
    }

    private final SignInBottomSheet getSignInBottomSheet() {
        return signInBottomSheet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.splashScreen = SplashScreen.INSTANCE.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        handleDataIfExists(intent);
        if (this.splashScreen != null) {
            this.splashScreen.setKeepOnScreenCondition(() -> keepSplashOn);
        }
        getViewModel2().verifyIsUserSigned();
        LinearLayout rlLoginGG = getBinding().rlLoginGG;
        ViewExtKt.m1100setOnSafeClickListenerdWUq8MI$default(rlLoginGG, 0L, null, () -> {
            launchSignInRequest();
            return Unit.INSTANCE;
        }, 3, null);
        LinearLayout rlOtherSignIn = getBinding().rlOtherSignIn;
        ViewExtKt.m1100setOnSafeClickListenerdWUq8MI$default(rlOtherSignIn, 0L, null, () -> {
            webViewNavigation.navigateToURL(SignInActivity.this, URLRoutes.terms);
            return Unit.INSTANCE;
        }, 3, null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            handleDataIfExists(intent);
        }
    }

    private void handleDataIfExists(Intent intent) {
        String valueOf = String.valueOf(intent.getData());
        if ("android.intent.action.VIEW".equals(intent.getAction())) {
            if (StringExtKt.isAuthLink(valueOf)) {
                viewModel.doSignIn(new SignInMethod.EmailLink(valueOf));
            } else {
                intentURL = valueOf;
            }
        }
    }

    @Override
    protected void reduceState(SignInState state) {
        ActivitySignInBinding binding = getBinding();
        binding.rlLoginGG.setClickable(!state.isLoading());
        ProgressBar buttonLoader = binding.buttonLoader;
        buttonLoader.setVisibility(state.isLoading() ? 0 : 8);
        TextView buttonText = binding.buttonText;
        buttonText.setVisibility(state.isLoading() ? 8 : 0);
        ImageView buttonIcon = binding.buttonIcon;
        buttonIcon.setVisibility(state.isLoading() ? 8 : 0);
        keepSplashOn = state.getShouldKeepSplashOn();
    }

    @Override
    protected void handleEffect(SignInEffects effect) {
        if (effect instanceof SignInEffects.NavigateToInputUsername) {
            navigateToInputUsername();
            return;
        }
        if (effect instanceof SignInEffects.NavigateToHome) {
            navigateToHome();
            return;
        }
        if (effect instanceof SignInEffects.ShowAuthLinkSentDialog) {
            EmailAuthDialog.INSTANCE.launchSentWarning(this);
            return;
        }
        if (effect instanceof SignInEffects.ShowFailedToSendLink) {
            POVToastKt.showToast$default(this, R.string.failed_to_send_auth_link, 0, 2, null);
        } else if (effect instanceof SignInEffects.ShowSignInFailedMessage) {
            POVToastKt.showToast$default(this, R.string.failed_to_sign_in, 0, 2, null);
        } else if (effect instanceof SignInEffects.ShowRestoreAccountDialog) {
            RestoreAccountDialogKt.showRestoreAccountDialog(this, ((SignInEffects.ShowRestoreAccountDialog) effect).getUserUid());
        }
    }

    private void navigateToInputUsername() {
        startActivity(new Intent(this, ProfileActivity.class));
        finish();
    }

    private void navigateToHome() {
        Intent putExtra = new Intent(this, HomeActivity.class).putExtra(HomeActivity.ARGS_KEY, intentURL);
        startActivity(putExtra);
        finish();
    }

    private void launchSignInRequest() {
        viewModel.startSignIn();
        signInBottomSheet.launch(this,
                it -> {
                    viewModel.doSignIn(new SignInMethod.Google(it));
                    return Unit.INSTANCE;
                },
                error -> {
                    viewModel.cancelSignIn();
                    String localizedMessage = error.getLocalizedMessage();
                    if (localizedMessage == null) {
                        localizedMessage = "";
                    }
                    POVToastKt.showToast$default(this, localizedMessage, 0, 2, null);
                    LogsKt.recordToCrashlytics$default(error, "Failed to begin one tap sign-in", null, 2, null);
                    EmailAuthDialog.INSTANCE.launchEmailRequest(this, email -> {
                        viewModel.sendAuthenticationLink(email);
                        return Unit.INSTANCE;
                    });
                    return Unit.INSTANCE;
                },
                () -> {
                    viewModel.cancelSignIn();
                    return Unit.INSTANCE;
                });
    }
}
