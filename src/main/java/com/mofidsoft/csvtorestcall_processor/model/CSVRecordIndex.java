package com.mofidsoft.csvtorestcall_processor.model;

import lombok.Getter;

/**
 * @author Milad Mofidi (milad.mofidi@gmail.com.com)
 * 7/3/2023
 */
@Getter
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

}
