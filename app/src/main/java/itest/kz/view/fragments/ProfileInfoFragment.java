package itest.kz.view.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.databinding.FragmentProfileInfoBinding;
import itest.kz.model.Profile;
import itest.kz.model.ProfileInfo;
import itest.kz.model.ProfileResponse;
import itest.kz.model.RegisterResponse;
import itest.kz.network.UserService;
import itest.kz.util.CameraUtils;
import itest.kz.util.CheckUtility;
import itest.kz.util.Constant;
import itest.kz.view.activity.AuthActivity;
import itest.kz.view.activity.HomeActivity;
import itest.kz.view.activity.MainHomeActivity;
import itest.kz.view.activity.SubjectActivity;
import itest.kz.viewmodel.ProfileInfoViewModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;

import static android.content.Context.MODE_PRIVATE;
import static itest.kz.util.InputValidator.hideKeyboard;

public class ProfileInfoFragment extends Fragment implements EasyPermissions.PermissionCallbacks
{
    private Profile profile;
    private FragmentProfileInfoBinding fragmentProfileInfoBinding;
    private ProfileInfoViewModel profileInfoViewModel;
    private Toolbar myToolbar;
    private TextView mainToolbarText;
    private String accessToken;
    private String language;
    private TextView dialogTextAuth;
    private Button buttonYesAuth;
    private Button buttonNoAuth;
    private EditText date;
    private String current = "";
    private String ddmmyyyy = "DDMMYYYY";
    private Calendar cal = Calendar.getInstance();

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                ((MainHomeActivity)(Activity)getContext()).setNavigationVisibiltity(true);
                closefragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void closefragment()
    {
        try {
            getFragmentManager().popBackStackImmediate();
        }
        catch (Exception e){}
    }



    public void getFromBundle()
    {
        profile = (Profile) getArguments().getSerializable(Constant.PROFILE);
    }
    public static ProfileInfoFragment newInstance(Profile profile)
    {

        Bundle args = new Bundle();
        args.putSerializable(Constant.PROFILE, profile);
        ProfileInfoFragment fragment = new ProfileInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        SharedPreferences settings = getContext().getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        setHasOptionsMenu(true);
        getFromBundle();
        getAccessToken();
        fragmentProfileInfoBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_profile_info,
                container, false);
        profileInfoViewModel = new ProfileInfoViewModel(getContext(), profile);
        fragmentProfileInfoBinding.setProfile(profileInfoViewModel);

        date = fragmentProfileInfoBinding.dateInput;

        date.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s)
            {
//                if (date.getText().length() == 2)
//                {
//                    date.setText(date.getText() + ".");
//                }
                // Do something after Text Change
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
//                date.setText("");
                // Do something before Text Change
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().equals(current)) {
                        String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                        String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                        int cl = clean.length();
                        int sel = cl;
                        for (int i = 2; i <= cl && i < 6; i += 2) {
                            sel++;
                        }
                        //Fix for pressing delete next to a forward slash
                        if (clean.equals(cleanC)) sel--;

                        if (clean.length() < 8){
                            clean = clean + ddmmyyyy.substring(clean.length());
                        }else{
                            //This part makes sure that when we finish entering numbers
                            //the date is correct, fixing it otherwise
                            int day  = Integer.parseInt(clean.substring(0,2));
                            int mon  = Integer.parseInt(clean.substring(2,4));
                            int year = Integer.parseInt(clean.substring(4,8));

                            mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                            cal.set(Calendar.MONTH, mon-1);
                            year = (year<1900)?1900:(year>2100)?2100:year;
                            cal.set(Calendar.YEAR, year);
                            // ^ first set year for the line below to work correctly
                            //with leap years - otherwise, date e.g. 29/02/2012
                            //would be automatically corrected to 28/02/2012

                            day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                            clean = String.format("%02d%02d%02d",day, mon, year);
                        }

                        clean = String.format("%s.%s.%s", clean.substring(0, 2),
                                clean.substring(2, 4),
                                clean.substring(4, 8));

                        sel = sel < 0 ? 0 : sel;
                        current = clean;
                        date.setText(current);
                        date.setSelection(sel < current.length() ? sel : current.length());
                    }

            }
        });
////

//        fragmentProfileInfoBinding.loginInput.setRawInputType(Configuration.KEYBOARD_QWERTY);
//        fragmentProfileInfoBinding.loginInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                fragmentProfileInfoBinding.loginInput.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        InputMethodManager imm = null;
//                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
//                        {
//                            imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                        }
//                        imm.showSoftInput(fragmentProfileInfoBinding.loginInput, InputMethodManager.SHOW_IMPLICIT);
//                    }
//                });
//            }
//        });
//        fragmentProfileInfoBinding.loginInput.requestFocus();
//        ((HomeActivity)getActivity()).setNavigationVisibility(false);
        getActivity().findViewById(R.id.bottom_navigation_view).setVisibility(View.GONE);
        setMyToolbar();
        initUI();
        initClickPhoto();
//        initEvent();

        return fragmentProfileInfoBinding.getRoot();
    }

    private void initClickPhoto()
    {
        fragmentProfileInfoBinding.photoImageView
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick (View v)
                    {


//                        Here is sample code for open gallery from app.

//                        Intent intent = new Intent();
//                        intent.setType("image/*");
//                        intent.setAction(Intent.ACTION_GET_CONTENT);
//                        startActivityForResult(Intent.createChooser(intent, "Select Picture"),999);

//
                        Intent chooserIntent = CameraUtils
                                .getImageFromGalleryCamera
                                        (v.getContext());
                        // set unique number for image calling
                        startActivityForResult(chooserIntent,
                                Constant.CAMERA_REQUEST_CODE);
                    }
                });
    }

//    @Override
//    void initUI ()
//    {
//        if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.CAMERA))
//        {
//            EasyPermissions.requestPermissions(this, S.camera_permission_message, I.CAMERA_REQUEST_CODE,
//                    Manifest.permission.CAMERA);
//        }
//    }

    void initUI ()
    {
        if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.CAMERA))
            {
                EasyPermissions.requestPermissions(this,
                        Constant.camera_permission_message,
                        Constant.CAMERA_REQUEST_CODE,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA});
            }
            else
            {
                EasyPermissions.requestPermissions(this,
                        Constant.camera_permission_message,
                        Constant.CAMERA_REQUEST_CODE,
                        Manifest.permission.READ_EXTERNAL_STORAGE);
            }

        }
        else if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.CAMERA))
        {
            EasyPermissions.requestPermissions(this,
                    Constant.camera_permission_message,
                    Constant.CAMERA_REQUEST_CODE,
                    Manifest.permission.CAMERA);
        }

    }

    public void getAccessToken()
    {
        accessToken = getActivity().getIntent().getStringExtra(Constant.ACCESS_TOKEN);
    }




    public void setMyToolbar()
    {
//        myToolbar = (Toolbar) fragmentProfileBinding.getRoot().findViewById(R.id.toolbar_profile);
        myToolbar = (Toolbar) fragmentProfileInfoBinding
                .toolbarProfile;
        mainToolbarText = (TextView) fragmentProfileInfoBinding
                .toolbarTitle;
        mainToolbarText.setTextColor(Color.WHITE);
        myToolbar.setTitle("");



        CardView saveBtn = (CardView) fragmentProfileInfoBinding.toolbarTextRight;
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                profileInfoViewModel.setProgress(true);
                TextView name = fragmentProfileInfoBinding.nameInput;
                TextView surname = fragmentProfileInfoBinding.surnameInput;
                TextView date = fragmentProfileInfoBinding.dateInput;
                TextView login = fragmentProfileInfoBinding.loginInput;
                TextView email = fragmentProfileInfoBinding.emailInput;

                ProfileInfo profileInfo = new ProfileInfo(name.getText().toString(),
                        surname.getText().toString(),
                        date.getText().toString(),
                        login.getText().toString(), email.getText().toString());
//

                if (CheckUtility.isNetworkConnected(getContext())) {

                    AppController appController = new AppController();
                    CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
                    UserService userService = appController.getUserService(accessToken);

                    Disposable disposable = userService.setProfile(
                            language,
                            name.getText().toString(), surname.getText().toString(),
                            date.getText().toString(), login.getText().toString(),
                            email.getText().toString()
                    )
                            .subscribeOn(appController.subscribeScheduler())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<ProfileResponse>() {
                                           @Override
                                           public void accept(ProfileResponse profileResponse) throws Exception {
//
//                                               System.out.println(profileResponse);
                                               profileInfoViewModel.setProgress(false);
                                               hideKeyboard(getActivity());
//                                               if (profileResponse.getProfile() != null)
//                                               {
                                               if (profileResponse.getProfile() != null) {
                                                   fragmentProfileInfoBinding.check.check();//won't work


//                                           profileInfoViewModel.setProgress(false);
                                                   new Handler().postDelayed(new Runnable() {
                                                       @Override
                                                       public void run() {
//                                                       profileInfoViewModel.setCheckMark(true);
                                                           fragmentProfileInfoBinding.check.uncheck();
                                                           try {
                                                               ((MainHomeActivity)(Activity)getContext()).setNavigationVisibiltity(true);
                                                               getFragmentManager().popBackStackImmediate();
                                                           }
                                                           catch (Exception e){}

                                                       }
                                                   }, 1500);


//                                               profileInfoViewModel.setCheckMark(false);
//                                           fragmentProfileInfoBinding.getProfile().setProfile(profileResponse.getProfile());

//                                               }
                                               }
                                               else
                                               {
                                                   Toast.makeText
                                                           (getContext(),
                                                                   profileResponse.getError(),
                                                                   Toast.LENGTH_LONG).show();
                                               }
                                           }

                                       },

                                    new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                            if (throwable.getMessage().contains("401")) {
                                                showToastUnauthorized();

                                            }
                                            profileInfoViewModel.setProgress(false);
                                        }
                                    }

                            );

                    compositeDisposable.add(disposable);
                }
                else
                {
                    profileInfoViewModel.setProgress(false);
                }

//                System.out.println(name.getText());
            }
        });



        ((AppCompatActivity)getActivity())
                .setSupportActionBar(myToolbar);
        ((AppCompatActivity)getActivity())
                .getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



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

            }
        });

        dialog.show();
    }

    public  void openAuthActivity()
    {
        Intent intent = new Intent(getContext(), AuthActivity.class);
        ((Activity)getContext()).startActivity(intent);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms)
    {
        if (requestCode == Constant.CAMERA_REQUEST_CODE)
        {
            Toast.makeText(getContext(), R.string.permission_granted, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                                         permissions,
                                         grantResults);

        EasyPermissions
                .onRequestPermissionsResult(Constant.CAMERA_REQUEST_CODE,
                                            permissions,
                                            grantResults,
                                  this);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms)
    {
        if (requestCode == Constant.CAMERA_REQUEST_CODE)
        {
            Toast.makeText(getContext(), R.string.permission_denied, Toast.LENGTH_LONG).show();
        }
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == 999)
            {
                try
                {
//                    Uri imageUri = CameraUtils.getImageUri(data);

//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());


                    //use imageUri as you see fit. load into imageView or upload to server using InputStream
                    Toast.makeText(getContext(),
                            CameraUtils.getImageUri(data).toString(),
                            Toast.LENGTH_SHORT).show();


//                    InputStream is = getActivity().getContentResolver().openInputStream(imageUri);

//                    File file = new File(new URI(imageUri.toString()));

//                    InputStream is = getActivity().getContentResolver().openInputStream(imageUri);
//                    Bitmap bitmap = BitmapFactory.decodeStream(is);
//                    is.close();

//                    String path = (CameraUtils
//                            .getRealPathFromUri(getContext(),
//                                    CameraUtils.getImageUri(data)
//                            ) == null) ? CameraUtils.getImageUri(data).toString()
//                            :
//                            CameraUtils.getRealPathFromUri
//                                    (getContext(),
//                                            CameraUtils.getImageUri(data));
                    File file;


//                    InputStream in = null;

                    if (data != null)
                    {
                        file = new File(CameraUtils
                            .getRealPathFromUri(getContext(),
                                    CameraUtils.getImageUri(data)));
//                        Bitmap bmp = BitmapFactory.decodeFile(CameraUtils.getImageUri(data).getPath());
//                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                        bmp.compress(Bitmap.CompressFormat.JPEG, 70, bos);
//                        in = new ByteArrayInputStream(bos.toByteArray());
//
//
                    }
//
                    else
                    {
                        file = new File(CameraUtils.getImageUri(data).getPath());


//                        ContentBody foto = new InputStreamBody(in, "image/jpeg", "filename");
                    }

                    profileInfoViewModel.setProgress(true);
                    Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    ExifInterface ei = new ExifInterface(file.getAbsolutePath());
                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);

                    Bitmap rotatedBitmap = null;
                    switch(orientation) {

                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotatedBitmap = rotateImage(bmp, 90);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotatedBitmap = rotateImage(bmp, 180);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotatedBitmap = rotateImage(bmp, 270);
                            break;

                        case ExifInterface.ORIENTATION_NORMAL:
                        default:
                            rotatedBitmap = bmp;
                    }

                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
                    InputStream in = new ByteArrayInputStream(bos.toByteArray());



                    getAccessToken();


                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), getBytes(in)
//                            getBytes(is)
                    );
//                    MediaType.parse(FileUtils.getFileExtension(imageFile.getAbsolutePath()))

//                    MediaType.parse("image/*")


                    MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);

                    AppController appController = new AppController();
                    CompositeDisposable compositeDisposable = new CompositeDisposable();

//                    profileInfoViewModel.setProgress(true);
//        AppController appController = AppController.create(context);
                    UserService userService = appController.getUserService(accessToken);

//                    System.out.println(accessToken);

                    Disposable disposable = userService.updateAvatar(
                            language,
                            body)
                            .subscribeOn(appController.subscribeScheduler())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<ProfileResponse>() {
                                           @Override
                                           public void accept(ProfileResponse profileResponse) throws Exception {

                                               profile.setAvatarUrl(profileResponse.getProfile().getAvatarUrl());
                                               profileInfoViewModel.getProfile().setAvatarUrl
                                                       (profileResponse.getProfile().getAvatarUrl());
                                               profileInfoViewModel.getInfoFromProfile();

                                               profileInfoViewModel.setProgress(false);

//                                               profileInfoViewModel.notify();
                                           }

//                                           @Override
//                                           public void accept(ResponseBody responseBody) throws Exception
//                                           {
//                                               System.out.println("Response");
//                                               System.out.println(responseBody.string());
//                                           }
                                       },
                                    new Consumer<Throwable>()
                                    {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception
                                        {
                                            if (throwable.getMessage().contains("401"))
                                            {
                                                showToastUnauthorized();

                                            }
                                            else
                                            {
                                                Toast.makeText(getContext(),
                                                        "size is big",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                            profileInfoViewModel.setProgress(false);

                                        }
                                    }

//                                    new Consumer<ProfileResponse>()
//                            {
//                                @Override
//                                public void accept(ProfileResponse profileResponse) throws Exception
//                                {
////                                    profile.setAvatarUrl(profileResponse.getProfile().getAvatarUrl());
//                                    System.out.println("Profile");
//                                    System.out.println(profileResponse.getProfile());
//                                    System.out.println("new avatar");
//                                }
//                            }
                            );

                    compositeDisposable.add(disposable);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}
