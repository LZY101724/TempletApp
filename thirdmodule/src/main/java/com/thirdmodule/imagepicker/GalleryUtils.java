package com.thirdmodule.imagepicker;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.thirdmodule.R;

import java.util.List;

/**
 * 图片选择器管理类
 *
 * @author lzy
 * create at 2018/11/2 15:08
 **/

public class GalleryUtils {

    private static final String TAG = "GalleryUtils";

    public static final int CAMERA_TYPE = 1;
    public static final int PICTURE_TYPE = 2;

    public static void chooseHead(Activity activity, int requestCode) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)
                .previewImage(true)
                .isCamera(true)
                .enableCrop(true)
                .circleDimmedLayer(true)
                .showCropFrame(false)
                .showCropGrid(false)
                .forResult(requestCode);

    }

    public static void chooseImage(Activity activity, int maxPhotoSum, List<LocalMedia> selectionMedia, int requestCode) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.MULTIPLE)
                .maxSelectNum(maxPhotoSum)
                .selectionMedia(selectionMedia)
                .previewImage(true)
                .isCamera(true)
                .enableCrop(false)
                .forResult(requestCode);

    }

    public static void chooseImage(Fragment fragment, int maxPhotoSum, List<LocalMedia> selectionMedia, int requestCode) {
        PictureSelector.create(fragment)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.MULTIPLE)
                .maxSelectNum(maxPhotoSum)
                .selectionMedia(selectionMedia)
                .previewImage(true)
                .isCamera(true)
                .enableCrop(false)
                .forResult(requestCode);

    }

    public static void previewImage(Activity activity, int position, List<LocalMedia> selectList) {
        PictureSelector.create(activity).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList);
    }

//  PictureSelector.create(MainActivity.this)
//  .openGallery()//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
// 	.theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
// 	.maxSelectNum()// 最大图片选择数量 int
// 	.minSelectNum()// 最小选择数量 int
//	.imageSpanCount(4)// 每行显示个数 int
// 	.selectionMode()// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
// 	.previewImage()// 是否可预览图片 true or false
// 	.previewVideo()// 是否可预览视频 true or false
//	.enablePreviewAudio() // 是否可播放音频 true or false
// 	.isCamera()// 是否显示拍照按钮 true or false
//	.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
//	.isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//	.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//	.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
// 	.enableCrop()// 是否裁剪 true or false
// 	.compress()// 是否压缩 true or false
// 	.glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
// 	.withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
// 	.hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
// 	.isGif()// 是否显示gif图片 true or false
//	.compressSavePath(getPath())//压缩图片保存地址
//    .freeStyleCropEnabled()// 裁剪框是否可拖拽 true or false
// 	.circleDimmedLayer()// 是否圆形裁剪 true or false
// 	.showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
// 	.showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
// 	.openClickSound()// 是否开启点击声音 true or false
// 	.selectionMedia()// 是否传入已选图片 List<LocalMedia> list
// 	.previewEggs()// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
// 	.cropCompressQuality()// 裁剪压缩质量 默认90 int
// 	.minimumCompressSize(100)// 小于100kb的图片不压缩
// 	.synOrAsy(true)//同步true或异步false 压缩 默认同步
// 	.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
// 	.rotateEnabled() // 裁剪是否可旋转图片 true or false
// 	.scaleEnabled()// 裁剪是否可放大缩小图片 true or false
// 	.videoQuality()// 视频录制质量 0 or 1 int
//	.videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//    .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//	.recordVideoSecond()//视频秒数录制 默认60s int
//	.isDragFrame(false)// 是否可拖动裁剪框(固定)
// 	.forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

}
