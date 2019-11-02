package com.izyver.pecodetest.toastshower.reflect;

import android.os.Handler;
import android.os.MessageQueue;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ReflectHelper {

    @Nullable
    Handler getHandler(@NonNull Toast toast) {
        Class<? extends Toast> toastClass = toast.getClass();

        try {
            Field mTNField = toastClass.getDeclaredField("mTN");
            mTNField.setAccessible(true);
            Object mTNObject = mTNField.get(toast);
            if (mTNObject == null) return null;
            Class mTNClass = mTNObject.getClass();

            Field mHandlerField = mTNClass.getDeclaredField("mHandler");
            mHandlerField.setAccessible(true);
            Object handler = mHandlerField.get(mTNObject);
            if (handler instanceof Handler) return (Handler) handler;
            else return null;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    MessageQueue getMessageQueue(@Nullable Handler handler) {
        try {
            if (handler == null) return null;
            Class<Handler> handlerClass = Handler.class;
            Field mQueueField = handlerClass.getDeclaredField("mQueue");
            mQueueField.setAccessible(true);
            Object messageQueue = mQueueField.get(handler);
            if (messageQueue instanceof MessageQueue) return (MessageQueue) messageQueue;
            else return null;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    boolean removeMessagesFromMessageQueue(MessageQueue queue, Handler handler, int... messages) {
        try {
            Class<MessageQueue> queueClass = MessageQueue.class;
            Method removeMessagesMethod = queueClass.getDeclaredMethod("removeMessages", Handler.class, int.class, Object.class);
            removeMessagesMethod.setAccessible(true);
            for (int message : messages) {
                removeMessagesMethod.invoke(queue, handler, message, null);
            }
            return true;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean setCallback(Handler handler, Handler.Callback callback){
        try {
            Class<Handler> handlerClass = Handler.class;
            Field mCallbackField = handlerClass.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);
            mCallbackField.set(handler, callback);
            return true;
        } catch (NoSuchFieldException|IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
}
