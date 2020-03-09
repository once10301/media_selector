package com.ly.media_selector;

import java.util.List;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

public class MediaSelectorPlugin implements MethodCallHandler {
    private MediaSelectorDelegate delegate;

    private MediaSelectorPlugin(MediaSelectorDelegate delegate) {
        this.delegate = delegate;
    }

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "media_selector");
        final MediaSelectorDelegate delegate = new MediaSelectorDelegate(registrar.activity());
        registrar.addActivityResultListener(delegate);
        channel.setMethodCallHandler(new MediaSelectorPlugin(delegate));
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if (call.method.equals("select")) {
            delegate.select(call, result);
        } else if (call.method.equals("preview_picture")) {
            List<String> selectList = call.argument("selectList");
            int position = call.argument("position");
            delegate.previewPicture(selectList, position);
        } else if (call.method.equals("preview_video")) {
            String path = call.argument("path");
            delegate.previewVideo(path);
        } else if (call.method.equals("clear_cache")) {
            delegate.clearCache();
        } else {
            result.notImplemented();
        }
    }
}
