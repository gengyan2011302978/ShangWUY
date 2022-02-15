package com.phjt.shangxueyuan.widgets;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class AppUpdate {
    public Builder pBar;
    public static String newVerCode;
    private String verName;
    private Context context;
    public static String a = "1";
    private Activity activity;
    private GetConfig getConfig;
    private float filelength;
    private float downlength;
    // 版本号集合
    private ProgressBar updateProgress;
    private TextView updatePercentage;
    private TextView updateDownlength;
    private TextView updateFilelength;
    private AlertDialog downloadAlertDialog;
    /**
     * 下载中断，重试对话框
     */
    private AlertDialog retryAlertDialog;
    private static final int FOR_SDCARD = 1;
    private static final int FOR_SYSTEM = 2;
    private String SDKVERSION2 = "2.2", SDKVERSION4 = "4.0";
    public static String EntranceTag;
    public boolean downloadOver = true;
    private Handler handler2;
    public AppUpdate(Context context, Activity activity, Handler handler) {
//		this.activity = (ShouYeActivity) activity;
//		if (activity instanceof ShouYeActivity) {
//			this.activity = (ShouYeActivity) activity;
//		} else {
        this.activity = activity;
//		}
        this.handler2 = handler;
        this.context = context;
//		mPhoneStateReceiver = new PhoneStateReceiver(handler);
//		registerPhoneReceiver();
//		softwareVersion = softWareVersionList.get(0);
    }

    // 把当前的页面与电话服务绑定
//	private void registerPhoneReceiver() {
//		IntentFilter filter = new IntentFilter(
//		// 监听电话状态
//				TelephonyManager.ACTION_PHONE_STATE_CHANGED);
//		this.context.registerReceiver(mPhoneStateReceiver, filter);
//	}

    // 把当前的页面与电话服务解绑
//	public void unRegisterPhoneReceiver() {
//		if (mPhoneStateReceiver != null) {
//			this.context.unregisterReceiver(mPhoneStateReceiver);
//		}
//	}

    /**
     * 方法说明：消息队列
     * */
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case 0:
                        // 设置进度条的最大值
                        updateProgress.setMax((int) filelength);
                        updateFilelength.setText((int) filelength / 1000 + "kb");
                        break;
                    case 1:
                        updateProgress.setProgress((int) downlength);
                        updateDownlength.setText((int) downlength / 1000 + "kb/");
                        updatePercentage.setText(format(downlength / filelength));
                        break;
                    case 2:
                        pBar.setCancelable(true);
                        if (downloadAlertDialog != null
                                && downloadAlertDialog.isShowing()) {
                            downloadAlertDialog.dismiss();
//						Toast.makeText(context, "文件下载完成",).show();
                            // Log.i("info", "下载完成。。。");
                        }
                        break;
                    case 10:
                        // Log.i("info", "来电");
                        if (!downloadOver) {
                            interruptDownload();// 下载中断
                            retryDialogShow();// 提示重新下载
                        }
                        break;
                    case -1:
                        break;
                    case 3:
                        xiaoxi.setVisibility(View.GONE);
                        rl.setVisibility(View.GONE);
                        break;
                    case 4:
//					showGuangGaoAndNotice();
                        break;
                    default:
                        break;
                }
            }
            super.handleMessage(msg);
        }

    };

    /**
     * 方法说明：启动异步任务
     * */
    public void startUpdateAsy() {
        doNewVersionUpdate();
        UpdateAsy updateAsy = new UpdateAsy();
        updateAsy.execute();
    }

    /**
     * 方法说明：与当前程序版本号做比较
     *
     * @return 升级--true 没变--false
     * */
    public boolean compareVerCode() {
//		if (!Tools.isEmpty(newVerCode) && newVerCode.equals("1")) {
//        new ClientContext().getOverallcache().put("cancelUpdate",
//                "cancelUpdate");
        return true;
//		} else {
//			if(Constant.gengxin.equals("gengduo")){
//				Constant.gengxin="";
//				Toast.makeText(context, "您当前版本为最新版本！",Toast.LENGTH_SHORT).show();
//			}

//			return false;
//		}
    }

    public void retryDialog() {
        Builder retry = new Builder(context);
        // Log.i("info", "提示重新下载对话框初始化。。。");
        retry = retry.setTitle("提示").setMessage("网络异常，下载失败")
                .setPositiveButton("重试", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        downloadOver = true;
//						updateDialogCreate();
                        dialog.cancel();
                    }
                });
//		if (softWareVersionList != null && softWareVersionList.get(0) != null) {
//			if (softWareVersionList.get(0).getMsg() == null) {
//				if (!softWareVersionList.get(0).getIsupdate().equals("1")) {
//					retry.setNegativeButton("取消", new OnClickListener() {
//
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							dialog.cancel();
//							downloadOver = true;
//						}
//					});
//
//				}
//			}
//		}
        retryAlertDialog = retry.create();
    }

    public void retryDialogShow() {
        if (null != retryAlertDialog && !retryAlertDialog.isShowing()) {
            // Log.i("info", "retryDialogShow---提示重新下载。。。");
            retryAlertDialog.show();
        } else if (null == retryAlertDialog) {
            retryDialog();
            retryAlertDialog.show();
        }
    }

    /**
     * 方法说明：无更新对话框提示
     * */

//	 ( public void notNewVersionShow() {
//
//	 verName = getConfig.getVerName(context); StringBuffer sb = new
//	 StringBuffer(); sb.append("当前版本:"); sb.append(verName);
//	 sb.append(",\n已是最新版,无需更新!"); Dialog dialog = new
//	 AlertDialog.Builder(context).setTitle("软件更新")
//	 .setMessage(sb.toString())// 设置内容 .setPositiveButton("确定",// 设置确定按钮 new
//	 DialogInterface.OnClickListener() {
//
//	 @Override public void onClick(DialogInterface dialog, int which) { }
//
//	 }).create();// 创建 // 显示对话框 dialog.show(); }

    /**
     * 方法说明：有更新对话框提示：
     * */
    private LinearLayout xiaoxi;
    private RelativeLayout rl;
    public void doNewVersionUpdate() {
//		Log.e("ddddddd", "hahhahahahahahhhah");

        //更新
//		if(Constant.UPDATE==0){
//			if(softwareVersion.getUpdate().equals("1")){
//        UpdateDialog1 dialog1 = new UpdateDialog1(context, activity);
//        if (true){
//            dialog1.setCanceledOnTouchOutside(false);
//        }else {
//            dialog1.setCanceledOnTouchOutside(true);
//        }
//
//        dialog1.setCancelable(true);
//        dialog1.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_SEARCH) {
//                    return true;
//                } else {
//                    return true; //默认返回 false，这里false不能屏蔽返回键，改成true就可以了
//                }
//            }
//        });
//				Constant.UPDATE=1;
//			}else{
//				if(Constant.gengxin.equals("gengduo")){
//					Constant.gengxin="";
//					Toast.makeText(context, "您当前已经是最新版本", Toast.LENGTH_SHORT).show();
//				}
    }
//		}
//		Log.e("我执行了", "哈哈哈哈哈哈哈哈哈哈");





    // verName = getConfig.getVerName(context);
    // StringBuffer sb = new StringBuffer();
    // sb.append("当前版本:");
    // sb.append(verName);
    // sb.append(", 发现新版本");
    // // sb.append(newVerCode);
    // sb.append(", 是否更新?");
    // LayoutInflater inflater = LayoutInflater.from(context);
    // View updateView = inflater.inflate(
    // R.layout.software_update_details_list, null);
    // TextView updatetTitle = (TextView) updateView
    // .findViewById(R.id.update_title);
    // updatetTitle.setText(sb.toString());
    // ListView softUpdateList = (ListView) updateView
    // .findViewById(R.id.update_list);
    // UpdateAdp updateAdp = new UpdateAdp(context, 0,
    // detailsCut(softWareVersionList.get(0).getUpdatedetails()));
    // softUpdateList.setAdapter(updateAdp);
    // Builder dialog = new AlertDialog.Builder(context);
    //
    //
    // if (softWareVersionList != null && softWareVersionList.get(0) !=
    // null) {
    // if (softWareVersionList.get(0).getIsupdate().equals("1")) {
    // dialog.setCancelable(false);
    //
    // }
    //
    // }
    // dialog.setTitle("软件更新");
    // dialog.setView(updateView);
    // // 设置内容
    // dialog.setPositiveButton("更新",// 设置确定按钮
    // new DialogInterface.OnClickListener() {
    //
    // @Override
    // public void onClick(DialogInterface dialog, int which) {
    // updateDialogCreate();
    // }
    // });
    // if (softWareVersionList != null && softWareVersionList.get(0) !=
    // null) {
    // if (softWareVersionList.get(0).getMsg() != null) {
    // if (!softWareVersionList.get(0).getIsupdate().equals("1")) {
    // dialog.setNegativeButton("暂不更新",
    // new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface dialog,
    // int whichButton) {
    // // 控制重复读取版本号
    // new ClientContext().getOverallcache().put(
    // "versionUpdate", "true");
    // new ClientContext().getOverallcache().put(
    // "cancelUpdate", "cancelUpdate");
    // }
    // });// 创建
    // }
    // }
    // }
    // // 显示对话框
    // downloadAlertDialog = dialog.create();
    // downloadAlertDialog.show();


    /*
     * 轮播图和通知小黄条
     */
//	public void showGuangGaoAndNotice(){
//		//滚动图片
//		if(null!=LunBoHuanChongQu.list){
//			if(null!=handler2){
//				handler2.sendEmptyMessage(1);
//			}
//
//		}
//
//		if (activity instanceof ShouYeActivity) {
//			//通知条
//			List<Notice> notices = softwareVersion
//					.getNotices();
//			xiaoxi = ((ShouYeActivity) activity).getXiaoxi();
//			if(null!=notices){
//				Log.e("notices", notices.toString());
//				TextView tv = null;
//				LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
//						LayoutParams.WRAP_CONTENT);
//				params.topMargin = 1;
//				for (Notice notice : notices) {
//					tv = new TextView(activity);
//					tv.setText(notice.getNotice());
//					tv.setTextColor(Color.parseColor("#f7592c"));
//					tv.setBackgroundColor(Color.parseColor("#fffbcf"));
//					tv.setLayoutParams(params);
//					tv.setGravity(Gravity.CENTER);
//					xiaoxi.addView(tv);
//				}
//
//				xiaoxi.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						Intent i = new Intent(activity, TongZhiChaKan.class);
//						activity.startActivity(i);
//					}
//				});
//			}
//			rl = (RelativeLayout) ((ShouYeActivity) activity).getDingdanguanlitishi();
//			if(softwareVersion.getDdtx().equals("1")){
//				rl.setVisibility(View.VISIBLE);
//			}
//			handler.sendEmptyMessageDelayed(3, 5000);
//		}
//
//
//	}



//	private void updateDialogCreate() {
//
//		downloadOver = false;// 开始下载
//
////		ClinetService.repairHymen(sf);
//		//控制重复读取版本号
//		if (new ClientContext().getOverallcache() != null) {
//			new ClientContext().getOverallcache().put("versionUpdate", "true");
//		}
//		View updateView = LayoutInflater.from(context).inflate(
//				R.layout.update_view, null);
//		updateProgress = (ProgressBar) updateView.findViewById(R.id.update_bar);
//		updatePercentage = (TextView) updateView
//				.findViewById(R.id.update_percentage);
//		updateDownlength = (TextView) updateView.findViewById(R.id.update_data);
//		updateFilelength = (TextView) updateView
//				.findViewById(R.id.update_data2);
//		pBar = new Builder(context);
//		updateProgress.setIndeterminate(false);
//		pBar.setTitle("正在下载");
//		pBar.setMessage("请稍候...");
//		pBar.setView(updateView);
//		pBar.setCancelable(false);
//
//		if (Environment.getExternalStorageState().equals(
//				Environment.MEDIA_MOUNTED)
//				&& MemorySpaceCheck.hasEnoughMemory(Environment
//						.getExternalStorageDirectory()
//						+ getConfig.UPDATE_SAVENAME)) {
////			String url = softWareVersionList.get(0).getDownloadUrl();
////			Log.i("SSSS", url);
//			downFile(WelcomeActivity.url, FOR_SDCARD);
//
//		} else if (MemorySpaceCheck.hasEnoughMemory(Environment
//				.getDataDirectory() + getConfig.UPDATE_SAVENAME)) {
//			downFile(WelcomeActivity.url, FOR_SYSTEM);
//		}
//
//	}

    /**
     * 方法说明：发送消息
     * */
    private void sendMsg(int flag) {
        Message msg = new Message();
        msg.what = flag;
        handler.sendMessage(msg);
    }

    /**
     * 方法说明：下载被中断
     */
    public void interruptDownload() {
        if (downloadAlertDialog != null && downloadAlertDialog.isShowing()) {
            // Log.i("info", "下载中断。。。");
            downloadAlertDialog.cancel();
            // if (thread != null) {
            // Log.i("info", "thread != null.....");
            // }
            // if (null != thread && thread.isAlive()) {
            // Log.i("info", "thread.isAlive().....");
            // thread.stop();
            // }
        }
    }

    Thread thread = null;

    /**
     * 方法说明：文件下载
     * */
    private void downFile(final String url, final int downTage) {
        downloadAlertDialog = pBar.create();
        downloadAlertDialog.show();
        thread = new Thread() {
            @Override
            public void run() {
//                HttpClient client = new DefaultHttpClient();
//                HttpGet get = new HttpGet(url);
//                HttpResponse response;
//                File file = null;
//                try {
//                    response = client.execute(get);
//                    HttpEntity entity = response.getEntity();
//                    filelength = entity.getContentLength();
//                    Log.i("SSSS", filelength + "长度");
//                    InputStream is = entity.getContent();
//                    FileOutputStream fileOutputStream = null;
//                    if (is != null) {
//
//                        if (downTage == FOR_SDCARD) {
//                            file = new File(
//                                    Environment.getExternalStorageDirectory(),
//                                    getConfig.UPDATE_SAVENAME);
//                            fileOutputStream = new FileOutputStream(file);
//
//                        } else if (downTage == FOR_SYSTEM) {
//
//                            /*
//                             * file = new File(Environment.getDataDirectory(),
//                             * getConfig.UPDATE_SAVENAME);
//                             */
//                            fileOutputStream = context.openFileOutput(
//                                    getConfig.UPDATE_SAVENAME,
//                                    Context.MODE_WORLD_READABLE);
//
//                        }
//
//                        byte[] buf = new byte[1024];
//
//                        sendMsg(0);// 给进度条set最大值
//                        int ch = -1;
//                        downlength = 0;
//                        while ((ch = is.read(buf)) != -1) {
//
//                            fileOutputStream.write(buf, 0, ch);
//                            downlength += ch;
//                            if (filelength > 0) {
//                                sendMsg(1);// 更新进度条
//                            }
//                        }
//
//                    }
//                    if (fileOutputStream != null) {
//                        fileOutputStream.flush();
//                        fileOutputStream.close();
//                        downloadOver = true;
//                        down();
//                    }
//                } catch (ClientProtocolException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

        };
        thread.start();
    }

    /**
     * 方法说明：
     * */
    private void down() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                sendMsg(2);// 通知进度条下载完成
                update();
            }
        });

    }

    /**
     * 方法说明：升级替换原程序
     * */
    private void update() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            intent.setDataAndType(
                    Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(),
                            GetConfig.UPDATE_SAVENAME)),
                    "application/vnd.android.package-archive");
        } else {

            /*
             * intent.setDataAndType(Uri.fromFile(new
             * File(context.getFileStreamPath(getConfig.UPDATE_SAVENAME) ,
             * getConfig.UPDATE_SAVENAME)),
             * "application/vnd.android.package-archive");
             */
            intent.setDataAndType(
                    Uri.fromFile(new File(context.getFileStreamPath(
                            GetConfig.UPDATE_SAVENAME).getPath())),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    /**
     * 方法说明：分割
     * */
    public static List<String> detailsCut(String details) {
        String[] s = details.split(";");
        List<String> list = new ArrayList<String>();
        int i;
        for (i = 0; i < s.length; i++) {
            list.add(s[i]);
        }
        return list;
    }

    /**
     * 方法说明：异步获取服务器程序版本号
     * */
    private class UpdateAsy extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String androidVersion;
            if (14 <= Build.VERSION.SDK_INT) {
                androidVersion = SDKVERSION4;
            } else {
                androidVersion = SDKVERSION2;
            }
//			softWareVersionList = getServerVerCode(androidVersion);
//			if (softWareVersionList != null) {
//				newVerCode = softWareVersionList.get(0).getUpdate();
//				softwareVersion = softWareVersionList.get(0);
//				handler.sendEmptyMessage(4);
//			}
//			Log.e("!!!!!!!!!!!!!", softWareVersionList.toString());

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println(activity.isFinishing());
//			if (mProgressDialog != null) {
//				if (activity != null && !activity.isFinishing()) {
//					mProgressDialog.dismiss();
//				}
//			}
            if (compareVerCode() && newVerCode != null) {
                doNewVersionUpdate();

            } else if ("MainActivity".equals(EntranceTag)) {
                // Toast.makeText(context, "您当前为最新版本",
                // Toast.LENGTH_SHORT).show();
                if ("2".equals(a)) {
//					Tools.toast().show(context, "已经是最新版本！");
                }

            }
            retryDialog();// 初始化重试对话框
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            onLoading();
            super.onPreExecute();
        }
    }

    /**
     * 方法说明：百分数转换
     * */
    private String format(double i) {
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumIntegerDigits(5);// 小数点前保留几位
        nf.setMinimumFractionDigits(0);
        return nf.format(i);
    }

    /**
     * 方法说明：版本号多小数点比较大小
     * */
    private int compare(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return 0;
        } else if (s1 == null) {
            return -1;
        } else if (s2 == null) {
            return 1;
        }

        String[] arr1 = s1.split("[^a-zA-Z0-9]+"), arr2 = s2
                .split("[^a-zA-Z0-9]+");

        int i1, i2, i3;

        for (int ii = 0, max = Math.min(arr1.length, arr2.length); ii <= max; ii++) {
            if (ii == arr1.length) {
                return ii == arr2.length ? 0 : -1;
            } else if (ii == arr2.length) {
                return 1;
            }

            try {
                i1 = Integer.parseInt(arr1[ii]);
            } catch (Exception x) {
                i1 = Integer.MAX_VALUE;
            }

            try {
                i2 = Integer.parseInt(arr2[ii]);
            } catch (Exception x) {
                i2 = Integer.MAX_VALUE;
            }

            if (i1 != i2) {
                return i1 - i2;
            }

            i3 = arr1[ii].compareTo(arr2[ii]);

            if (i3 != 0) {
                return i3;
            }
        }

        return 0;
    }

    /**
     * 方法说明：加载数据
     */
    private void onLoading() {
        // mProgressDialog = ProgressDialog.show(context, null, "数据加载中...");
        // mProgressDialog = new YJKProgressBarView(activity);
        // mProgressDialog.setCancelable(true);
        // mProgressDialog.show();
    }

    /*
     * public boolean isUpdateTage() { return updateTage; } public void
     * setUpdateTage(boolean updateTage) { this.updateTage = updateTage; }
     */
    private class UpdateAdp extends ArrayAdapter<String> {
        List<String> detailsList;

        public UpdateAdp(Context context, int textViewResourceId,
                         List<String> detailsList) {
            super(context, textViewResourceId, detailsList);
            this.detailsList = detailsList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//			convertView = LayoutInflater.from(context.getApplicationContext())
//					.inflate(R.layout.software_update_deta_list_item, null);
//			ViewHolder vh = new ViewHolder();
//			vh.updateDetails = (TextView) convertView
//					.findViewById(R.id.update_details);
//			vh.updateDetails.setText(detailsList.get(position));
            return convertView;
        }

    }

    class ViewHolder {
        TextView updateDetails;
    }
}
