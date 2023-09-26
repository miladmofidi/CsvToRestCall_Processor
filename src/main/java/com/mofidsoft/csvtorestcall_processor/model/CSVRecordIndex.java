package com.mofidsoft.csvtorestcall_processor.model;

/**
 * @author Milad Mofidi (milad.mofidi@cmas-systems.com)
 * 7/3/2023
 */
public enum CSVRecordIndex {
	GROUP( 0 ),
	EMPRESA( 1 ),
	NUMERO_CONTRIBUINTE( 2 ),
	TIPO( 3 ),
	MORADA( 4 );

	private final int index;

	CSVRecordIndex( int index ) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
