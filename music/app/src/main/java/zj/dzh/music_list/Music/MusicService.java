package zj.dzh.music_list.Music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.RequiresApi;

import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service {

    private MediaPlayer player; //定义多媒体播放器变量
    private Timer timer;        //定义计时器变量
    public MusicService() {}    //构造函数
    @Override
    public  IBinder onBind(Intent intent){
        return new MusicControl();
    }
    @Override
    public void onCreate(){
        super.onCreate();
        player=new MediaPlayer();//创建音乐播放器对象
    }
    //添加计时器用于设置音乐播放器中的播放进度条
    public void addTimer(){
        if(timer == null){
            //创建计时器对象
            timer = new Timer();
            TimerTask task = new TimerTask() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    if (player == null) return;
                    int duration = player.getDuration();//获取歌曲总时长
                    int currentPosition = player.getCurrentPosition();//获取播放进度
                    Message msg = MusicActivity.handler.obtainMessage();//创建消息对象
                    //将音乐的总时长和播放进度封装至消息对象中
                    Bundle bundle = new Bundle();
                    bundle.putInt("duration", duration);
                    bundle.putInt("currentPosition", currentPosition);
                    msg.setData(bundle);
                    //将消息发送到主线程的消息队列
                    MusicActivity.handler.sendMessage(msg);
                }
            };
            //开始计时任务后的5毫秒，第一次执行task任务，以后每500毫秒执行一次
            timer.schedule(task,5,500);
        }
    }
    //定义音乐播放控制类
    class MusicControl extends Binder{//Binder是一种跨进程的通信方式
        //播放音乐
        public void play(int i){
                //String path
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + "music" + i);
                try{
                    //重置音乐播放器
                    player.reset();
                    //加载多媒体文件
                    player = MediaPlayer.create(getApplicationContext(), uri);
                    //播放音乐
                    player.start();
                    //添加计时器
                    addTimer();
                }catch(Exception e){
                e.printStackTrace();
            }
        }

        public void pausePlay(){
            player.pause();              //暂停播放音乐
        }
        public void continuePlay(){
            player.start();              //继续播放音乐
        }
        public void seekTo(int progress){
            player.seekTo(progress);     //设置音乐的播放位置
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(player == null) return;
        if(player.isPlaying()) player.stop();//停止播放音乐
        player.release();//释放占用的资源
        player = null;//将player置为空
    }
}
