package com.github.mikephil.charting.data;

/**
 * Entry class for the BarChart. (especially stacked bars)
 * 
 * @author Philipp Jahoda
 */
public class BarEntry extends Entry {

	/** the values the stacked barchart holds */
	private float[] mVals;

	/** the sum of all negative values this entry (if stacked) contains */
	private float mNegativeSum;

	/** the sum of all positive values this entry (if stacked) contains */
	private float mPositiveSum;

	/** shadow-high value */
	private float mShadowHigh = 0f;

	/** shadow-low value */
	private float mShadowLow = 0f;

	/** close value */
	private float mClose = 0f;

	/** open value */
	private float mOpen = 0f;

	/**
	 * Constructor for stacked bar entries.
	 * 
	 * @param vals
	 *            - the stack values, use at lest 2
	 * @param xIndex
	 */
	public BarEntry(float[] vals, int xIndex) {
		super(calcSum(vals), xIndex);

		this.mVals = vals;
		calcPosNegSum();
	}

	/**
	 * Constructor.
	 *
	 * @param xIndex The index on the x-axis.
	 * @param shadowH The (shadow) high value.
	 * @param shadowL The (shadow) low value.
	 * @param open The open value.
	 * @param close The close value.
	 */
	public BarEntry(int xIndex, float shadowH, float shadowL, float open, float close, float val) {
		super(val,xIndex);

		this.mShadowHigh = shadowH;
		this.mShadowLow = shadowL;
		this.mOpen = open;
		this.mClose = close;
	}

	/**
	 * Constructor for normal bars (not stacked).
	 * 
	 * @param val
	 * @param xIndex
	 */
	public BarEntry(float val, int xIndex) {
		super(val, xIndex);
	}

	/**
	 * Constructor for stacked bar entries.
	 * 
	 * @param vals
	 *            - the stack values, use at least 2
	 * @param xIndex
	 * @param label
	 *            Additional description label.
	 */
	public BarEntry(float[] vals, int xIndex, String label) {
		super(calcSum(vals), xIndex, label);

		this.mVals = vals;
		calcPosNegSum();
	}

	/**
	 * Constructor for normal bars (not stacked).
	 * 
	 * @param val
	 * @param xIndex
	 * @param data
	 *            Spot for additional data this Entry represents.
	 */
	public BarEntry(float val, int xIndex, Object data) {
		super(val, xIndex, data);
	}

	/**
	 * Returns an exact copy of the BarEntry.
	 */
	public BarEntry copy() {

		BarEntry copied = new BarEntry(getVal(), getXIndex(), getData());
		copied.setVals(mVals);
		return copied;
	}

	/**
	 * Returns the stacked values this BarEntry represents, or null, if only a single value is represented (then, use
	 * getVal()).
	 * 
	 * @return
	 */
	public float[] getVals() {
		return mVals;
	}

	/**
	 * Set the array of values this BarEntry should represent.
	 * 
	 * @param vals
	 */
	public void setVals(float[] vals) {
		setVal(calcSum(vals));
		mVals = vals;
		calcPosNegSum();
	}

	/**
	 * Returns the value of this BarEntry. If the entry is stacked, it returns the positive sum of all values.
	 * 
	 * @return
	 */
	@Override
	public float getVal() {
		return super.getVal();
	}


	/**
	 * Returns the upper shadows highest value.
	 *
	 * @return
	 */
	public float getHigh() {
		return mShadowHigh;
	}

	public void setHigh(float mShadowHigh) {
		this.mShadowHigh = mShadowHigh;
	}

	/**
	 * Returns the lower shadows lowest value.
	 *
	 * @return
	 */
	public float getLow() {
		return mShadowLow;
	}

	public void setLow(float mShadowLow) {
		this.mShadowLow = mShadowLow;
	}

	/**
	 * Returns the bodys close value.
	 *
	 * @return
	 */
	public float getClose() {
		return mClose;
	}

	public void setClose(float mClose) {
		this.mClose = mClose;
	}

	/**
	 * Returns the bodys open value.
	 *
	 * @return
	 */
	public float getOpen() {
		return mOpen;
	}

	public void setOpen(float mOpen) {
		this.mOpen = mOpen;
	}

	/**
	 * Returns true if this BarEntry is stacked (has a values array), false if not.
	 * 
	 * @return
	 */
	public boolean isStacked() {
		return mVals != null;
	}

	public float getBelowSum(int stackIndex) {

		if (mVals == null)
			return 0;

		float remainder = 0f;
		int index = mVals.length - 1;

		while (index > stackIndex && index >= 0) {
			remainder += mVals[index];
			index--;
		}

		return remainder;
	}

	/**
	 * Reuturns the sum of all positive values this entry (if stacked) contains.
	 * 
	 * @return
	 */
	public float getPositiveSum() {
		return mPositiveSum;
	}

	/**
	 * Returns the sum of all negative values this entry (if stacked) contains. (this is a positive number)
	 * 
	 * @return
	 */
	public float getNegativeSum() {
		return mNegativeSum;
	}

	private void calcPosNegSum() {

		if (mVals == null) {
			mNegativeSum = 0;
			mPositiveSum = 0;
			return;
		}

		float sumNeg = 0f;
		float sumPos = 0f;

		for (float f : mVals) {
			if (f <= 0f)
				sumNeg += Math.abs(f);
			else
				sumPos += f;
		}

		mNegativeSum = sumNeg;
		mPositiveSum = sumPos;
	}

	/**
	 * Calculates the sum across all values of the given stack.
	 *
	 * @param vals
	 * @return
	 */
	private static float calcSum(float[] vals) {

		if (vals == null)
			return 0f;

		float sum = 0f;

		for (float f : vals)
			sum += f;

		return sum;
	}
}
