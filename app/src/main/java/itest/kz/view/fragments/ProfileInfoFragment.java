package itest.kz.view.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import itest.kz.util.Constant;
import itest.kz.view.activity.HomeActivity;
import itest.kz.view.activity.SubjectActivity;
import itest.kz.viewmodel.ProfileInfoViewModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;

import static android.content.Context.MODE_PRIVATE;

public class ProfileInfoFragment extends Fragment implements EasyPermissions.PermissionCallbacks
{
    private Profile profile;
    private FragmentProfileInfoBinding fragmentProfileInfoBinding;
    private ProfileInfoViewModel profileInfoViewModel;
    private Toolbar myToolbar;
    private TextView mainToolbarText;
    private String accessToken;
    private String language;

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                closefragment();
                return true;
//            case R.id.menu_save:
//                System.out.println("save");
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void closefragment()
    {

        getFragmentManager().popBackStackImmediate();
    }

    //    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
//    {
//        inflater.inflate(R.menu.main, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }

//    @Override
//    public void onPrepareOptionsMenu(Menu menu)
//    {
//        if(Build.VERSION.SDK_INT > 11)
//        {
//            menu.findItem(R.id.menu_github).setVisible(false);
//            menu.findItem(R.id.menu_github1).setVisible(false);
//            menu.findItem(R.id.menu_github2).setVisible(false);
//            menu.findItem(R.id.menu_save).setVisible(false);
//            menu.findItem(R.id.menu_logout).setVisible(false);
//        }
//        super.onPrepareOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        switch (item.getItemId())
//        {
//            case R.id.menu_logout:
////                clearSharedPreferences();
////                goToMainActivity();
//                getActivity().finish();
//                return true;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }


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
        ((HomeActivity)getActivity()).setNavigationVisibility(false);
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
            EasyPermissions.requestPermissions(this,
                    Constant.camera_permission_message,
                    Constant.CAMERA_REQUEST_CODE,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.CAMERA))
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


//
//    void initEvent ()
//    {
//        fragmentProfileInfoBinding
//                .getRoot()
//                .findViewById(R.id.btn_get_image)
//                .setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick (View v)
//            {
//                Intent chooserIntent = CameraUtils.getImageFromGalleryCamera(v.getContext());
//                // set unique number for image calling
//                startActivityForResult(chooserIntent, 999);
//            }
//        });
//    }


    public void setMyToolbar()
    {
//        myToolbar = (Toolbar) fragmentProfileBinding.getRoot().findViewById(R.id.toolbar_profile);
        myToolbar = (Toolbar) fragmentProfileInfoBinding
                .toolbarProfile;
        mainToolbarText = (TextView) fragmentProfileInfoBinding
                .toolbarTitle;
        mainToolbarText.setTextColor(Color.WHITE);
        myToolbar.setTitle("");

        TextView saveBtn = (TextView) fragmentProfileInfoBinding.toolbarTextRight;
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                TextView name = fragmentProfileInfoBinding.nameInput;
                TextView surname = fragmentProfileInfoBinding.surnameInput;
                TextView date = fragmentProfileInfoBinding.dateInput;
                TextView login = fragmentProfileInfoBinding.loginInput;
                TextView email = fragmentProfileInfoBinding.emailInput;

                ProfileInfo profileInfo = new ProfileInfo(name.getText().toString(),
                        surname.getText().toString(),
                        date.getText().toString(),
                        login.getText().toString(), email.getText().toString());
//                System.out.println(accessToken);

                AppController appController = new AppController();
                CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
                UserService userService = appController.getUserService();

                Disposable disposable = userService.setProfile(Constant.ACCEPT,
                        language, "Bearer " + accessToken,
//                        "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjI1NTU0MTEzYjcwM2EzNzgzMGFlYjFjYjZkMWI3YzI3NzQwY2MyMTNmZTk2YmYyMTc5ZTY4ZGQ1MTdhNWY3NzYxMDIyYzI2ZmM3NzQ3MzExIn0.eyJhdWQiOiIxIiwianRpIjoiMjU1NTQxMTNiNzAzYTM3ODMwYWViMWNiNmQxYjdjMjc3NDBjYzIxM2ZlOTZiZjIxNzllNjhkZDUxN2E1Zjc3NjEwMjJjMjZmYzc3NDczMTEiLCJpYXQiOjE1NTM1MDI4MTEsIm5iZiI6MTU1MzUwMjgxMSwiZXhwIjoxNTg1MTI1MjExLCJzdWIiOiIxMjEyNTIwIiwic2NvcGVzIjpbXX0.o_AqAxMWQf2h7BHDRH1QFzUvJ5jrVYA8PuLtlGXxIjHSygf1pKmuiifzxHwYshPHTqdZEUmRvgCFokAM349RoFDgkekI2RPA_mGsQ_UaCCuyMCN5xBIRObE_YBl8CDMX6rGhJwHQsmuVQXwnC9BnwFl_7OdjFo7AZM_05ZdjEweLX-gcFYAlOpdi0qfsueN2LUNTEseHPwgTFFxPCNpL8JPh98hSGSmo0OsKaJUHRy1ggYXarAsYoO5rS-vK_zVo_MLDm0ADyV-yVuVfjisNn0EVaXd3pC1q3GC76YAuJlmv20Cme0XIpgBF33lWICswnehpPRkqqo_OUTmwvUgJwT2d2cQlTFO59IHeFoHi2JwrH4kE07E-HHLXhiOnnL0McsqX-l_ofF5Nal8KK5xKmFg9ha2W8-tSmsyYMTtNYgXQw2OX2OgmeC1fVNIHHmdw9-hjwyJrm_ojjc9owbb53FQNaE6YTBSSHv5cDJlebCr0Zz2u1cKScujLH9Zq3V_eDkvLq_G-wQxhuRbSjo_Sc9T2no5KaHAP96Kiv9GOhkxOpdvem3LyolKcKeL3QIMvAU9cj3kidnlLY_WyMEg8um4Msam8k9fJ6JryIbKkTQsTc0Wh3BExgf3pNDayDvkTV-3NHRCv9XDHIIsSMQg_JgSiR8A3SQJuxmXWtnkhFJA",
//                        "1234","123", "10.10.1010",
//                            "test123@gmail.com", "test123@gmail.com"
                        name.getText().toString(), surname.getText().toString(),
                        date.getText().toString(), login.getText().toString(),
                        email.getText().toString()
                )
                        .subscribeOn(appController.subscribeScheduler())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ProfileResponse>()
                                   {
                                       @Override
                                       public void accept(ProfileResponse profileResponse) throws Exception
                                       {
//                                           System.out.println("Cool");
                                           fragmentProfileInfoBinding.getProfile();

                                       }
                                   }
//                                new Consumer<RegisterResponse>() {
//                            @Override
//                            public void accept(RegisterResponse registerResponse) throws Exception
//                            {
//                                Toast toast;
//                                toast = Toast.makeText(context.getApplicationContext(),
//                                        registerResponse.getMessage(),
//                                        Toast.LENGTH_LONG);
//                                toast.show();
//                            }
//                        }
                        );

                compositeDisposable.add(disposable);

//                System.out.println(name.getText());
            }
        });

//        myToolbar.setTitle("Өңдеу");
//        myToolbar.setTitleTextColor(Color.WHITE);
//        myToolbar.setContentInsetsAbsolute(200, 0);
//        myToolbar.setContentInsetsAbsolute(myToolbar.getContentInsetLeft(), 100);

        ((AppCompatActivity)getActivity())
                .setSupportActionBar(myToolbar);
        ((AppCompatActivity)getActivity())
                .getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
//        AppController appController = AppController.create(context);
                    UserService userService = appController.getUserService();

//                    System.out.println(accessToken);

                    Disposable disposable = userService.updateAvatar(Constant.ACCEPT,
                            language,

                            "Bearer " + accessToken,
//                            Constant.MULTIPART,
                            body
//                            requestFile
                    )
                            .subscribeOn(appController.subscribeScheduler())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<ProfileResponse>() {
                                           @Override
                                           public void accept(ProfileResponse profileResponse) throws Exception {
                                               profile.setAvatarUrl(profileResponse.getProfile().getAvatarUrl());
                                               profileInfoViewModel.getProfile().setAvatarUrl
                                                       (profileResponse.getProfile().getAvatarUrl());
                                               profileInfoViewModel.getInfoFromProfile();
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
                                            Toast.makeText(getContext(),
                                                    "size is big",
                                                    Toast.LENGTH_SHORT).show();
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
