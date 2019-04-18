package itest.kz.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
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
        notifyChange();
//        notifyAll();
    }

    public Profile getProfile()
    {
        return profile;
    }
    public int getAgreementText()
    {
        if (language.equals(Constant.KZ))
            return R.string.agreementKz;
        return R.string.agreementRu;
    }

    public int getLangText()
    {
        if (language.equals(Constant.KZ))
            return R.string.langKz;
        return R.string.langRu;
    }

    public int getPasswordText()
    {
        if (language.equals(Constant.KZ))
            return R.string.passwordChangedKz;
        return R.string.passwordChangedRu;
    }

    public int getInfoText()
    {
        if (language.equals(Constant.KZ))
            return R.string.profileInfoKz;
        return R.string.profileInfoRu;
    }

    public  ProfileFragmentViewModel(Context context, Profile profile)
    {
        this.context = context;
        this.profile = profile;
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        profileImage = new ObservableField<>();
        getInfoFromProfile();
        this.clickInfo = () ->
        {
            openFragment();
        };

        this.clickPassword = () ->
        {
//            System.out.println("click Password");
            openPasswordFragment();
        };
        this.clickLang = () ->
        {
            openLangFragment();
        };
        this.clickAgreement = () ->
        {
            openAgreementFragment();
        };


    }

    private void openAgreementFragment()
    {
        FragmentHelper.openFragment(context,
                R.id.frame_layout,
                new AgreementFragment());
    }

    private void openLangFragment()
    {
        FragmentHelper.openFragment(context,
                R.id.frame_layout,
                new LanguageChangeFragment());
    }

    private void openPasswordFragment()
    {


        FragmentHelper.openFragment(context,
                R.id.frame_layout,
//                R.id.viewpager,
                new PasswordChangeFragment());


//        FrameLayout frameLayout = (FrameLayout) context.findViewById(R.id.viewpager);
////        activitySubjectBinding.viewpager.setVisibility(View.VISIBLE);
//        frameLayout.setVisibility(View.VISIBLE);
//        getFragmentManager(context).beginTransaction()
//                .replace(
////                        R.id.viewpager,
//                        R.id.frame_layout,
//                        new PasswordChangeFragment(), "passwordFragment")
//                .addToBackStack(null)
//                .commit();
//        FragmentHelper.openFragment(context,
//                R.id.viewpager,
//                new PasswordChangeFragment());
    }

    private void openFragment()
    {
//        System.out.println(profileResponse.getProfile());
        FragmentHelper.openFragment(context,
                R.id.frame_layout,
//                R.id.viewpager,
                ProfileInfoFragment.newInstance(profile));
    }

    public Action getClickInfo()
    {
        return clickInfo;
    }

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

    private void getInfoFromProfile()
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
}
