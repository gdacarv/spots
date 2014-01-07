package com.dcc.matc89.spots.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

public class CheckboxesDropdownView extends DropdownView{
	
	private ArrayList<Boolean> mCheckeds;
	private CheckBoxesAdapter mAdapter;
	private OnDropdownCheckedChangeListener mListener;

	public CheckboxesDropdownView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CheckboxesDropdownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CheckboxesDropdownView(Context context) {
		super(context);
		init();
	}
	
	public void setOnDropdownCheckedChangeListener(OnDropdownCheckedChangeListener listener){
		mListener = listener;
	}
	
	public void setItems(List<?> items){
		mCheckeds = new ArrayList<Boolean>(items.size());
		for(int i = 0; i < items.size(); i++)
			mCheckeds.add(Boolean.FALSE);
		mAdapter.clear();
		mAdapter.addAll(items);
	}
	
	public void setCheckedAll(boolean checked){
		Boolean isChecked = checked;
		for(int i = 0; i < mCheckeds.size(); i++)
			mCheckeds.set(i, isChecked);
		mAdapter.notifyDataSetChanged();
	}
	
	public boolean[] getState(){
		boolean[] state = new boolean[mCheckeds.size()];
		for(int i = 0; i < state.length; i++)
			state[i] = mCheckeds.get(i).booleanValue();
		return state;
	}
	
	@Override
	public void setContentView(View view) {
		throw new UnsupportedOperationException("Don't use setContentView on a CheckboxesDropdownView.");
	}

	private void init(){
		super.setContentView(getListView());
	}

	private View getListView() {
		Context context = getContext();
		ListView list = new ListView(context);
		mAdapter = new CheckBoxesAdapter(context);
		list.setAdapter(mAdapter);
		return list;
	}
	
	
	private class CheckBoxesAdapter extends ArrayAdapter<Object> {

		public CheckBoxesAdapter(Context context) {
			super(context, 0, new ArrayList<Object>());
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				convertView = new CheckBox(getContext());
				((CheckBox)convertView).setOnCheckedChangeListener(onCheckedChangeListener);
			}
			
			convertView.setTag(null);
			((CheckBox)convertView).setText(getItem(position).toString());
			((CheckBox)convertView).setChecked(mCheckeds.size() > position ? mCheckeds.get(position) : false);
			convertView.setTag(position);
			return convertView;
		}
	}
	
	private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			Integer index = (Integer) buttonView.getTag();
			if(index == null)
				return;
			mCheckeds.set(index, isChecked);
			if(mListener != null)
				mListener.onCheckedChanged(CheckboxesDropdownView.this, index, isChecked);
		}
	};
	
	public interface OnDropdownCheckedChangeListener {
		void onCheckedChanged(CheckboxesDropdownView picker, int index, boolean isChecked);
	}
}
