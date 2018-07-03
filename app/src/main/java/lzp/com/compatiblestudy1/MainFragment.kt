package lzp.com.compatiblestudy1

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by li.zhipeng on 2018/6/26.
 */
class MainFragment: Fragment() {

    private lateinit var contentTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contentTextView = view.findViewById(R.id.content)

        // 找到按钮设置点击事件：跳转到第二个Activity
        // 如果不需要使用Button的特性，就不需要强转，这样省去了强转的时间成本
        view.findViewById<View>(R.id.button).setOnClickListener {
            startActivityForResult(Intent(requireActivity(), SecondActivity::class.java), 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100){
            contentTextView.text = data?.getStringExtra("text")
        }

    }

}