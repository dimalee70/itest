package itest.kz.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
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
import itest.kz.view.fragments.ProfileFragment;
import itest.kz.view.fragments.ProfileInfoFragment;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ProfileFragmentViewModel extends BaseObservable
{
    private Context context;
    private String accessToken;
    private Profile profile;
    private BindableFieldTarget bindableFieldTarget;
    private ObservableField<Drawable> profileImage;
    private Action clickInfo;

    public  ProfileFragmentViewModel(Context context, Profile profile)
    {
        this.context = context;
        this.profile = profile;
        profileImage = new ObservableField<>();
        getInfoFromProfile();
        this.clickInfo = () ->
        {
            openFragment(profile);
        };


    }

    private void openFragment(Profile profile)
    {
//        System.out.println(profileResponse.getProfile());
        FragmentHelper.openFragment(context,
                R.id.frame_layout,
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
