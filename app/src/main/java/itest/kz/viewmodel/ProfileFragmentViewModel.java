package itest.kz.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.model.Profile;
import itest.kz.model.ProfileResponse;
import itest.kz.model.RegisterResponse;
import itest.kz.network.UserService;
import itest.kz.util.BindableFieldTarget;
import itest.kz.util.Constant;
import itest.kz.util.FragmentHelper;
import itest.kz.view.activity.MainHomeActivity;
import itest.kz.view.fragments.AgreementFragment;
import itest.kz.view.fragments.LanguageChangeFragment;
import itest.kz.view.fragments.PasswordChangeFragment;
import itest.kz.view.fragments.ProfileFragment;
import itest.kz.view.fragments.ProfileInfoFragment;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static android.content.Context.MODE_PRIVATE;
import static itest.kz.util.FragmentHelper.getFragmentManager;

public class ProfileFragmentViewModel extends BaseObservable
{
    private Context context;
    private String accessToken;
    private Profile profile;
    private BindableFieldTarget bindableFieldTarget;
    private ObservableField<Drawable> profileImage;
    public Action clickInfo;
    public Action clickPassword;
    public Action clickLang;
    public Action clickAgreement;
    private String language;
    public ObservableInt progress = new ObservableInt(View.GONE);
    public ObservableInt scroll = new ObservableInt(View.GONE);
    public ObservableInt imageButtonVisibility;
    public ObservableInt getProgress()
    {
        return progress;
    }

    public int getServerErrorText()
    {
        return R.string.tryAgainText;
    }

    public void setProgress(boolean isProgress)
    {
        if (isProgress)
            progress.set(View.VISIBLE);
        else
        {
            progress.set(View.GONE);
            scroll.set(View.VISIBLE);
        }
        notifyChange();
    }

    //    infoText
//    passwordText
//    langText
//    agreementText

    public void setLanguage(String language)
    {
        this.language = language;
        notifyChange();
    }

    public void setProfile(Profile profile)
    {
        this.profile = profile;
        new ProfileFragmentViewModel(context, profile);
        notifyChange();
//        notifyAll();
    }

    public Profile getProfile()
    {
        return profile;
    }
    public int getAgreementText()
    {
        return R.string.helpCenter;
    }

    public int getLangText()
    {
        return R.string.lang;
    }

    public int getPasswordText()
    {
        return R.string.passwordChanged;
    }

    public int getInfoText()
    {
        return R.string.profileInfo;
    }

    public  ProfileFragmentViewModel(Context context, Profile profile)
    {
        this.context = context;
//        ((MainHomeActivity)(Activity)context).showBottonNavigation();
        this.profile = profile;
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        profileImage = new ObservableField<>();
        this.imageButtonVisibility = new ObservableInt(View.GONE);
        getInfoFromProfile();

        this.clickPassword = () ->
        {
//            System.out.println("click Password");
            ((MainHomeActivity)(Activity)context).setNavigationVisibiltity(false);
            Fragment fragment = new PasswordChangeFragment();
            openFragment(fragment);

        };
        this.clickInfo = () ->
        {
            ((MainHomeActivity)(Activity)context).setNavigationVisibiltity(false);
            Fragment fragment = ProfileInfoFragment.newInstance(profile);
            openFragment(fragment);

        };


        this.clickLang = () ->
        {
            ((MainHomeActivity)(Activity)context).setNavigationVisibiltity(false);
            Fragment fragment = new LanguageChangeFragment();
//            Fragment fragment = ProfileInfoFragment.newInstance(profile);
            openFragment(fragment);


        };
        this.clickAgreement = () ->
        {
            ((MainHomeActivity)(Activity)context).setNavigationVisibiltity(false);
            Fragment fragment = new AgreementFragment();
//            Fragment fragment = ProfileInfoFragment.newInstance(profile);
            openFragment(fragment);

        };


    }

//    private void openAgreementFragment()
//    {
//        FragmentHelper.openFragment(context,
//                R.id.frame_layout,
//                new AgreementFragment());
//    }
//
//    private void openLangFragment()
//    {
//        FragmentHelper.openFragment(context,
//                R.id.frame_layout,
//                new LanguageChangeFragment());
//    }
//
//    private void openPasswordFragment()
//    {
//
//        PasswordChangeFragment passwordChangeFragment = new PasswordChangeFragment();
////        passwordChangeFragment.getView().setOnKeyListener( new View.OnKeyListener()
////        {
////            @Override
////            public boolean onKey( View v, int keyCode, KeyEvent event )
////            {
////                if( keyCode == KeyEvent.KEYCODE_BACK )
////                {
////                    return true;
////                }
////                return false;
////            }
////        } );
//        FragmentHelper.openFragment(context,
//                R.id.frame_layout,
//                passwordChangeFragment
////                R.id.viewpager,
//                );
//
//
////        FrameLayout frameLayout = (FrameLayout) context.findViewById(R.id.viewpager);
//////        activitySubjectBinding.viewpager.setVisibility(View.VISIBLE);
////        frameLayout.setVisibility(View.VISIBLE);
////        getFragmentManager(context).beginTransaction()
////                .replace(
//////                        R.id.viewpager,
////                        R.id.frame_layout,
////                        new PasswordChangeFragment(), "passwordFragment")
////                .addToBackStack(null)
////                .commit();
////        FragmentHelper.openFragment(context,
////                R.id.viewpager,
////                new PasswordChangeFragment());
//    }

    private void openFragment(Fragment fragment)
    {
//        System.out.println(profileResponse.getProfile());
        FragmentHelper.openFragment(context,
                R.id.frame_layout,
//                R.id.viewpager,
                fragment
                );
    }

    public Action getClickInfo()
    {
        return clickInfo;
    }

//    public void setProfileImage(ObservableField<Drawable> profileImage) {
//        this.profileImage = profileImage;
//    }

    public ObservableField<Drawable> getProfileImage()
    {
        return profileImage;
    }

    public String getName()
    {
        return profile.getFirstName() + " " + profile.getSurName();
    }

    public String getEmail()
    {
        return profile.getEmail();
    }

    public void getInfoFromProfile()
    {
        bindableFieldTarget = new BindableFieldTarget(profileImage, context.getResources());

        Picasso
                .get()
                .load((profile.getAvatarUrl() == null ||
                        profile.getAvatarUrl().equals("")?
                        Constant.EMPTY_PHOTO :
                        profile.getAvatarUrl()))
                .transform(new CropCircleTransformation())
//                .centerInside()
//            .fit()
                .into(bindableFieldTarget);
    }

    public int getLogOutText()
    {
        return R.string.logout;
    }
}
