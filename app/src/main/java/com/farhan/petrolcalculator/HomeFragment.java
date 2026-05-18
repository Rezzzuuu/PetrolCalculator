package com.farhan.petrolcalculator;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.farhan.petrolcalculator.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    // Petrol prices (RM/liter)
    private static final double PRICE_RON95 = 3.87;
    private static final double PRICE_RON97 = 4.70;
    private static final double PRICE_DIESEL = 4.87;
    private static final double BUDI_SUBSIDY_RATE = 1.99;

    // Currently selected petrol type
    private String selectedType = "RON95";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Default selection: RON95
        selectPetrolType("RON95");
        binding.etPricePerLiter.setText(String.format("%.2f", PRICE_RON95));

        // Petrol type click listeners
        binding.btnRON95.setOnClickListener(v -> {
            selectPetrolType("RON95");
            binding.etPricePerLiter.setText(String.format("%.2f", PRICE_RON95));
        });

        binding.btnRON97.setOnClickListener(v -> {
            selectPetrolType("RON97");
            binding.etPricePerLiter.setText(String.format("%.2f", PRICE_RON97));
        });

        binding.btnDiesel.setOnClickListener(v -> {
            selectPetrolType("Diesel");
            binding.etPricePerLiter.setText(String.format("%.2f", PRICE_DIESEL));
        });

        // Calculate button
        binding.btnCalculate.setOnClickListener(v -> calculate());

        // Reset button
        binding.btnReset.setOnClickListener(v -> resetForm());
    }

    private void selectPetrolType(String type) {
        selectedType = type;

        // Reset all chips to unselected style
        setChipStyle(binding.btnRON95, false);
        setChipStyle(binding.btnRON97, false);
        setChipStyle(binding.btnDiesel, false);

        // Set text colours back to dark for unselected
        updateChipText(binding.btnRON95, false);
        updateChipText(binding.btnRON97, false);
        updateChipText(binding.btnDiesel, false);

        // Apply selected style to the right chip
        switch (type) {
            case "RON95":
                setChipStyle(binding.btnRON95, true);
                updateChipText(binding.btnRON95, true);
                break;
            case "RON97":
                setChipStyle(binding.btnRON97, true);
                updateChipText(binding.btnRON97, true);
                break;
            case "Diesel":
                setChipStyle(binding.btnDiesel, true);
                updateChipText(binding.btnDiesel, true);
                break;
        }
    }

    private void setChipStyle(LinearLayout chip, boolean selected) {
        if (selected) {
            chip.setBackgroundResource(R.drawable.petrol_chip_selected);
        } else {
            chip.setBackgroundResource(R.drawable.petrol_chip_unselected);
        }
    }

    private void updateChipText(LinearLayout chip, boolean selected) {
        // Update primary label (index 0) and subtitle (index 1)
        if (chip.getChildCount() >= 3) {
            // The RadioButton is at index 0 (gone), label at 1, subtitle at 2
            View labelView = chip.getChildAt(1);
            View subView = chip.getChildAt(2);
            if (labelView instanceof TextView) {
                ((TextView) labelView).setTextColor(selected
                        ? ContextCompat.getColor(requireContext(), R.color.white)
                        : ContextCompat.getColor(requireContext(), R.color.deep_blue));
            }
            if (subView instanceof TextView) {
                ((TextView) subView).setTextColor(selected
                        ? 0xFFCCDDFF
                        : ContextCompat.getColor(requireContext(), R.color.text_secondary));
            }
        }
    }

    private void calculate() {
        // Validate inputs
        String priceStr = binding.etPricePerLiter.getText() != null
                ? binding.etPricePerLiter.getText().toString().trim() : "";
        String usageStr = binding.etFuelUsage.getText() != null
                ? binding.etFuelUsage.getText().toString().trim() : "";

        if (priceStr.isEmpty()) {
            binding.etPricePerLiter.setError("Please enter price per liter");
            binding.etPricePerLiter.requestFocus();
            return;
        }
        if (usageStr.isEmpty()) {
            binding.etFuelUsage.setError("Please enter fuel usage");
            binding.etFuelUsage.requestFocus();
            return;
        }

        double pricePerLiter = Double.parseDouble(priceStr);
        double fuelUsage = Double.parseDouble(usageStr);

        if (pricePerLiter <= 0) {
            Toast.makeText(requireContext(), "Price must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }
        if (fuelUsage <= 0) {
            Toast.makeText(requireContext(), "Fuel usage must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isBudiEligible = binding.switchBudi.isChecked();

        // ── Step 1: Total petrol cost ──
        double totalCost = fuelUsage * pricePerLiter;

        // ── Step 2: BUDI rebate ──
        double budiRebate = 0.0;
        if (isBudiEligible && selectedType.equals("RON95")) {
            budiRebate = fuelUsage * BUDI_SUBSIDY_RATE;
        }

        // ── Step 3: Total saving (Final Amount to Pay) ──
        double totalSaving = totalCost - budiRebate;

        // Show results
        binding.tvTotalCost.setText(String.format("RM %.2f", totalCost));

        if (budiRebate > 0) {
            // FIXED: Variables are now correctly mapped to their UI elements
            binding.tvBudiRebate.setText(String.format("RM %.2f", budiRebate));
            binding.tvTotalSaving.setText(String.format("RM %.2f", totalSaving));
            binding.tvSavedAmount.setText(String.format("You saved RM %.2f!", budiRebate));

            binding.rowBudiRebate.setVisibility(View.VISIBLE);
            binding.dividerSaving.setVisibility(View.VISIBLE);
            binding.rowTotalSaving.setVisibility(View.VISIBLE);
        } else {
            binding.rowBudiRebate.setVisibility(View.GONE);
            binding.dividerSaving.setVisibility(View.GONE);
            binding.rowTotalSaving.setVisibility(View.GONE);

            if (isBudiEligible && !selectedType.equals("RON95")) {
                Toast.makeText(requireContext(),
                        "BUDI Rebate only applies to RON95 fuel",
                        Toast.LENGTH_LONG).show();
            }
        }

        binding.cardResults.setVisibility(View.VISIBLE);

        // Smooth scroll to results
        binding.cardResults.post(() -> {
            View scrollParent = (View) binding.cardResults.getParent().getParent();
            if (scrollParent instanceof android.widget.ScrollView) {
                ((android.widget.ScrollView) scrollParent)
                        .smoothScrollTo(0, binding.cardResults.getTop());
            }
        });
    }

    private void resetForm() {
        selectPetrolType("RON95");
        binding.etPricePerLiter.setText(String.format("%.2f", PRICE_RON95));
        binding.etFuelUsage.setText("");
        binding.switchBudi.setChecked(false);
        binding.cardResults.setVisibility(View.GONE);
        binding.rowBudiRebate.setVisibility(View.GONE);
        binding.dividerSaving.setVisibility(View.GONE);
        binding.rowTotalSaving.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}