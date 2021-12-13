package com.example.bankmanagement.view.create_profile

import androidx.recyclerview.widget.DiffUtil
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseBindingListAdapter
import com.example.bankmanagement.base.adapter.BaseBindingViewHolder
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.ItemImageBinding
import com.example.bankmanagement.models.ProofOfIncome
import com.example.bankmanagement.utils.Utils


class ProofOfIncomeImageAdapter(private  val onItemDeleted: BaseItemClickListener<ProofOfIncome>) : BaseBindingListAdapter<ProofOfIncome>(
    DiffCallback(),
) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_image;
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder<ProofOfIncome>, position: Int) {

        val binding = (holder.binding as ItemImageBinding);
        val item = getItem(position);
        binding.imageUri=item.getPathOrUrl().toString();

        binding.deleteImage.setOnClickListener{
            onItemDeleted.onItemClick(position,item);
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<ProofOfIncome>() {
        override fun areItemsTheSame(
            oldItem: ProofOfIncome,
            newItem: ProofOfIncome
        ): Boolean {
            return oldItem == newItem;
        }

        override fun areContentsTheSame(
            oldItem: ProofOfIncome,
            newItem: ProofOfIncome
        ): Boolean {
            return false
        }

    }
}