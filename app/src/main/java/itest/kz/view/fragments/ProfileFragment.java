package itest.kz.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.databinding.FragmentProfileBinding;
import itest.kz.model.Profile;
import itest.kz.model.ProfileResponse;
import itest.kz.network.UserService;
import itest.kz.util.Constant;
import itest.kz.view.activity.HomeActivity;
import itest.kz.view.activity.MainActivity;
import itest.kz.viewmodel.ProfileFragmentViewModel;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment
{
    private FragmentProfileBinding fragmentProfileBinding;
    private ProfileFragmentViewModel profileFragmentViewModel;
    private Toolbar myToolbar;
    private SharedPreferences sharedPreferences;
    private Profile profile;
    private String language;
    private boolean isStartedFirs = true;
    private String accessToken;


    public static ProfileFragment newInstance(Profile profile)
    {

        Bundle args = new Bundle();
        args.putSerializable(Constant.PROFILE, profile);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_logout:
                clearSharedPreferences();
                goToMainActivity();
                getActivity().finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void clearSharedPreferences()
    {
        sharedPreferences = getActivity().getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();
    }

    public void goToMainActivity()
    {
        Intent intent = new Intent(getContext(),MainActivity.class);
        startActivity(intent);
    }

    public void getFromBundle()
    {
        Bundle args = getArguments();
        profile = (Profile) args.getSerializable(Constant.PROFILE);
    }


    private void updateProfile(Profile profile)
    {
        this.profile = profile;
//        profileFragmentViewModel = new ProfileFragmentViewModel(getContext(), profile);
//        fragmentProfileBinding.setProfile(profileFragmentViewModel);
//        setMyToolbar();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu)
    {
        if(Build.VERSION.SDK_INT > 11)
        {
            menu.findItem(R.id.menu_github).setVisible(false);
            menu.findItem(R.id.menu_github1).setVisible(false);
            menu.findItem(R.id.menu_github2).setVisible(false);
            menu.findItem(R.id.menu_save).setVisible(false);
            menu.findItem(R.id.menu_logout).setVisible(true);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {


        SharedPreferences settings = getContext().getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        setHasOptionsMenu(true);

        fragmentProfileBinding = DataBindingUtil.inflate
                (inflater, R.layout.fragment_profile, container, false);

        ((HomeActivity)getActivity()).setNavigationVisibility(true);
////        getFromBundle();
//
//        getFromBundle();
//        profileFragmentViewModel = new ProfileFragmentViewModel(getContext(), profile);
//        fragmentProfileBinding.setProfile(profileFragmentViewModel);


//

        if (isStartedFirs)
        {
            getFromBundle();
            profileFragmentViewModel = new ProfileFragmentViewModel(getContext(), profile);

            isStartedFirs = false;
        }
        else
            fetchProfileInfo();

        setMyToolbar();
        fragmentProfileBinding.setProfile(profileFragmentViewModel);


//        System.out.println(isStartedFirs);


//        System.out.println(isStartedFirs);
//        setContentProfile();



//        TextView mTitle = (TextView) myToolbar.findViewById(R.id.toolbar_title);
//        mTitle.setTextColor(Color.WHITE);

        return fragmentProfileBinding.getRoot();
    }

    public void fetchProfileInfo()
    {
        getAccessToken();
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        UserService userService = appController.getUserService();

        Disposable disposable = userService.getProfile(language, Constant.ACCEPT,
                "Bearer " + accessToken)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProfileResponse>()
                {

                    @Override
                    public void accept(ProfileResponse profileResponse) throws Exception
                    {
                        profileFragmentViewModel.setProfile(profileResponse.getProfile());
                        profileFragmentViewModel.setLanguage(language);
                    }
                });

        compositeDisposable.add(disposable);
    }

//    public void setContentProfile()
//    {
//        String photo1 = "http://i.imgur.com/DvpvklR.png";
//        Picasso
//                .get()
//                .load((photo1 == null ||
//                        photo1.equals("")?
//                        Constant.EMPTY_PHOTO :
//                        photo1))
//                .transform(new CropCircleTransformation())
////                .centerInside()
//                .fit()
//                .into(fragmentProfileBinding.profilePhoto);
//
//    }

    public void setMyToolbar()
    {
//        myToolbar = (Toolbar) fragmentProfileBinding.getRoot().findViewById(R.id.toolbar_profile);
        myToolbar = (Toolbar) fragmentProfileBinding.toolbarProfile;
        TextView titleTextView = fragmentProfileBinding.toolbarTitle;
        titleTextView.setText("Профиль");
        myToolbar.setTitle("");
//        myToolbar.setTitleTextColor(Color.WHITE);
//        myToolbar.setContentInsetsAbsolute(200, 0);

//        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        upArrow.setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);

        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
    }

    public void getAccessToken()
    {
        accessToken = getActivity().getIntent().getStringExtra(Constant.ACCESS_TOKEN);
    }
}
