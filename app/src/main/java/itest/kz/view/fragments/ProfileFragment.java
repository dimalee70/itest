package itest.kz.view.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import itest.kz.util.CheckUtility;
import itest.kz.util.Constant;
import itest.kz.view.activity.AuthActivity;
import itest.kz.view.activity.HomeActivity;
import itest.kz.view.activity.MainActivity;
import itest.kz.view.activity.MainHomeActivity;
import itest.kz.view.activity.MaterialsActivity;
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
    private TextView dialogTextAuth;
    private Button buttonYesAuth;
    private Button buttonNoAuth;


    public static ProfileFragment newInstance(Profile profile)
    {

        Bundle args = new Bundle();
        args.putSerializable(Constant.PROFILE, profile);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
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
        Intent intent = new Intent(getContext(),AuthActivity.class);
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
        profileFragmentViewModel = new ProfileFragmentViewModel(getContext(), profile);
        fragmentProfileBinding.setProfile(profileFragmentViewModel);
//        setMyToolbar();
    }

//    @Override
//    public void onPrepareOptionsMenu(Menu menu)
//    {
//        if(Build.VERSION.SDK_INT > 11)
//        {
//            menu.findItem(R.id.menu_github).setVisible(false);
//            menu.findItem(R.id.menu_github1).setVisible(false);
//            menu.findItem(R.id.menu_github2).setVisible(false);
//            menu.findItem(R.id.menu_save).setVisible(false);
//            menu.findItem(R.id.menu_logout).setVisible(true);
//        }
//        super.onPrepareOptionsMenu(menu);
//    }

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

//        ((HomeActivity)getActivity()).setNavigationVisibility(true);
        fragmentProfileBinding.logoutCardview
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        clearSharedPreferences();
                        goToMainActivity();
                        getActivity().finish();
                    }
                });




////        getFromBundle();
//
//        getFromBundle();
//        profileFragmentViewModel = new ProfileFragmentViewModel(getContext(), profile);
//        fragmentProfileBinding.setProfile(profileFragmentViewModel);



        if (isStartedFirs)
        {
            getFromBundle();
            profileFragmentViewModel = new ProfileFragmentViewModel(getContext(), profile);

            isStartedFirs = false;
//            System.out.println("First");
        }
//        else {
//            fetchProfileInfo();
//            System.out.println("Second");
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    ((Activity)getContext()).finish();
////                    ((Activity)getContext()).overridePendingTransition( 0, 0);
//                    ((Activity)getContext()).startActivity(((Activity)getContext()).getIntent());
////                    ((Activity)getContext()).overridePendingTransition( 0, 0);
//                }});
//            thread.start();
//        }



//                BottomNavigationView bottomNavigationView = (BottomNavigationView) ((Activity)getContext()).findViewById (R.id.bottom_navigation_view);
//                bottomNavigationView.getMenu().getItem(0).setTitle(R.string.mainNavigation);

        setMyToolbar();
        fragmentProfileBinding.setProfile(profileFragmentViewModel);
//        ((MainHomeActivity)getActivity()).hideBottonNavigation();



        return fragmentProfileBinding.getRoot();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        fetchProfileInfo();
        System.out.println("On Resume");
    }

    public void fetchProfileInfo()
    {
        profileFragmentViewModel.setProgress(true);
        getAccessToken();

        if (CheckUtility.isNetworkConnected(getContext())) {
            AppController appController = new AppController();
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            UserService userService = appController.getUserService(accessToken);

            Disposable disposable = userService.getProfile(language)
                    .subscribeOn(appController.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ProfileResponse>() {

                                   @Override
                                   public void accept(ProfileResponse profileResponse) throws Exception {
                                       profile = profileResponse.getProfile();
//                                       System.out.println(profile);
                                       updateProfile(profile);
                                       profileFragmentViewModel.setProfile(profile);
                                       profileFragmentViewModel.getInfoFromProfile();
                                       profileFragmentViewModel.setLanguage(language);
                                       profileFragmentViewModel.setProgress(false);
                                   }
                               },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    if (throwable.getMessage().contains("401")) {
                                        showToastUnauthorized();
                                        profileFragmentViewModel.setProgress(false);
                                    }
//                                System.out.println(throwable.getLocalizedMessage());
//                                System.out.println(throwable.getMessage());

                                }
                            }
                    );

            compositeDisposable.add(disposable);
        }
        else
        {
            profileFragmentViewModel.setProgress(false);
        }
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


//    private TextView dialogTextAuth;
//    private Button buttonYesAuth;
//    private Button buttonNoAuth;

    public void showToastUnauthorized()
    {

//        public void showFinishTimeDialog()
//        {
        Dialog dialog = new Dialog(getContext());
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                openAuthActivity();
//                    finishTest(testIdMain);
                //System.out.println(testIdMain);//103080954
//                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogTextAuth = dialog.findViewById(R.id.dialog_text);
        buttonYesAuth = dialog.findViewById(R.id.buttonOk);
        buttonNoAuth = dialog.findViewById(R.id.buttonCancel);
        buttonNoAuth.setVisibility(View.GONE);
        buttonYesAuth.setText(R.string.ok);
        dialogTextAuth.setText(R.string.sessionError);
        buttonYesAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonYesAuth.setEnabled(false);
                dialog.dismiss();
                openAuthActivity();
//                    finishTest(testIdMain);
                //System.out.println(testIdMain);//103080954

            }
        });

//        buttonNo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
        dialog.show();
    }

    public  void openAuthActivity()
    {
        Intent intent = new Intent(getContext(), AuthActivity.class);
        ((Activity)getContext()).startActivity(intent);
//        if (language.equals(Constant.KZ))
//
//            Toast.makeText(this,
//                    R.string.sessionErrorKz,
//                    Toast.LENGTH_SHORT).show();
//        else
//        {
//            Toast.makeText(this,
//                    R.string.sessionErrorRu,
//                    Toast.LENGTH_SHORT).show();
//        }
    }


    public void setMyToolbar()
    {
//        myToolbar = (Toolbar) fragmentProfileBinding.getRoot().findViewById(R.id.toolbar_profile);
        myToolbar = (Toolbar) fragmentProfileBinding.toolbarProfile;
        TextView titleTextView = fragmentProfileBinding.toolbarTitle;
        titleTextView.setText("Профиль");
        myToolbar.setTitle("");

        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
    }

    public void getAccessToken()
    {
        accessToken = getActivity().getIntent().getStringExtra(Constant.ACCESS_TOKEN);
    }
}
