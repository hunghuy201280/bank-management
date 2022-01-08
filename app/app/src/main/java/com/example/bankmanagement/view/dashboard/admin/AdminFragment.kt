package com.example.bankmanagement.view.dashboard.admin

import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import com.example.bankmanagement.R
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.databinding.FragmentAdminBinding
import com.example.bankmanagement.view.dashboard.admin.add_balance.CreateDepositDialogFragment
import com.example.bankmanagement.view_models.dashboard.admin.AdminViewModel
import com.example.bankmanagement.widgets.adapter.CustomSpinnerAdapter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class AdminFragment : BaseFragment<FragmentAdminBinding, AdminViewModel>(), BaseUserView {


    private val TAG = "AdminFragment";
    override fun layoutRes(): Int = R.layout.fragment_admin


    override val viewModel: AdminViewModel by viewModels()


    override fun viewModelClass(): Class<AdminViewModel> = AdminViewModel::class.java

    val revenueByTypeAdapter = RevenueByTypeAdapter()

    override fun initViewModel(viewModel: AdminViewModel) {
        binding.viewModel = viewModel;
        viewModel.init(this)

        viewModel.revenueStatistic.observe(this) { it ->
            revenueByTypeAdapter.submitList(it.revenueByType.map { mapIt ->
                Pair(
                    mapIt.key,
                    mapIt.value
                )
            })
        }
    }

    override fun initView() {
        initSpinner()
        initLineChart()

        binding.addBalanceBtn.setOnClickListener {
            CreateDepositDialogFragment().show(childFragmentManager, CreateDepositDialogFragment.TAG)
        }
//
//        //region Loan type dropdown
//        val loanTypes = LoanType.getFilterValues()
//        val loanTypesAdapter = CustomSpinnerAdapter(requireContext(), R.layout.list_item, loanTypes)
//        loanTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.loanTypeDropDown.adapter = loanTypesAdapter
//
//        //endregion
//
//        //region Loan status dropdown
//        val loanStatuses = LoanStatus.getValues();
//        val loanStatusesAdapter =
//            CustomSpinnerAdapter(requireContext(), R.layout.list_item, loanStatuses)
//        binding.loanStatusDropdown.adapter = loanStatusesAdapter
//
//        //endregion
    }

    private fun initSpinner() {
        val yearList = mutableListOf<String>()
        for (i in 2000..2022) {
            yearList.add(i.toString())
        }
        yearList.reverse()
        val yearSpinnerAdapter = CustomSpinnerAdapter(requireContext(), R.layout.list_item, yearList)
        binding.yearDropdown.adapter = yearSpinnerAdapter
        //Set default position
        val defaultPosition = yearSpinnerAdapter.getPosition("2021")
        binding.yearDropdown.setSelection(defaultPosition)
        binding.yearDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.getStatistic(parent?.selectedItem as String)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun initLineChart() {
        val xAxisValues: List<String> = ArrayList(
            listOf(
                "",
                "Jan",
                "Feb",
                "Mar",
                "Apr",
                "May",
                "Jun",
                "Jul",
                "Aug",
                "Sep",
                "Oct",
                "Nov",
                "Dec"
            )
        )
        binding.lineChart.let {
            it.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)
            it.xAxis.position = XAxis.XAxisPosition.BOTTOM
            it.xAxis.setDrawAxisLine(false)
            it.xAxis.setDrawGridLines(false)
//        it.axisRight.setGridDashedLine(DashPathEffect(floatArrayOf(1f, 1f), 0f))
            it.axisRight.isEnabled = false
            it.legend.isEnabled = false
            it.description.isEnabled = false
            it.animateXY(5000, 5000)
            it.invalidate()
        }
    }

    override fun initData() {
        binding.revenueByTypeRV.adapter = revenueByTypeAdapter
    }

    override fun initAction() {
        viewModel.revenueStatistic.observe(this) {
            initDataLineChart(it.revenueByMonth)
        }
//        binding.loanTypeDropDown.onItemSelectedListener =
//            object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
//                    viewModel.selectedLoanType.value = LoanType.values()[position];
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {}
//            }

//        binding.loanStatusDropdown.onItemSelectedListener =
//            object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
//                    viewModel.loanStatus.value = LoanStatus.values()[position];
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                }
//            }
//
//        viewModel.selectedLoanType.observe(this, {
//            binding.loanTypeDropDown.setSelection(LoanType.values().indexOf(it), true)
//        });
//        viewModel.loanStatus.observe(this, {
//            binding.loanStatusDropdown.setSelection(LoanStatus.values().indexOf(it), true)
//        })

    }

    private fun initDataLineChart(listValue: Map<String, Double>) {
        val yValues = arrayListOf<Entry>()
        listValue.values.forEachIndexed { index, value ->
            yValues.add(Entry((index + 1).toFloat(), value.toFloat()))
        }
        val lineDataSet = LineDataSet(yValues, "Data Set 1")
        lineDataSet.lineWidth = 2f
        //Line and highlight color
        lineDataSet.setCircleColor(R.color.cornflower_blue_chart_primary)
        lineDataSet.color = R.color.cornflower_blue_chart_primary

        //Fill color
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillColor = R.color.cornflower_blue_chart_secondary

        //Disable value in line
        lineDataSet.setDrawValues(false)

        //Set draw line curve and animation
        lineDataSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER

        val dataSet = arrayListOf<ILineDataSet>()
        dataSet.add(lineDataSet)
        val lineData = LineData(dataSet)
        binding.lineChart.data = lineData
        binding.lineChart.invalidate()
    }
}