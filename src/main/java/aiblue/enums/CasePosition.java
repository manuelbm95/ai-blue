//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package aiblue.enums;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT)
public enum CasePosition implements Serializable {
	A8(-4, 4, 'a', 8, 1), B8(-3, 4, 'b', 8, 2), C8(-2, 4, 'c', 8, 3), D8(-1, 4, 'd', 8, 4), E8(0, 4, 'e', 8, 5),
	F8(1, 4, 'f', 8, 6), G8(2, 4, 'g', 8, 7), H8(3, 4, 'h', 8, 8), A7(-4, 3, 'a', 7, 1), B7(-3, 3, 'b', 7, 2),
	C7(-2, 3, 'c', 7, 3), D7(-1, 3, 'd', 7, 4), E7(0, 3, 'e', 7, 5), F7(1, 3, 'f', 7, 6), G7(2, 3, 'g', 7, 7),
	H7(3, 3, 'h', 7, 8), A6(-4, 2, 'a', 6, 1), B6(-3, 2, 'b', 6, 2), C6(-2, 2, 'c', 6, 3), D6(-1, 2, 'd', 6, 4),
	E6(0, 2, 'e', 6, 5), F6(1, 2, 'f', 6, 6), G6(2, 2, 'g', 6, 7), H6(3, 2, 'h', 6, 8), A5(-4, 1, 'a', 5, 1),
	B5(-3, 1, 'b', 5, 2), C5(-2, 1, 'c', 5, 3), D5(-1, 1, 'd', 5, 4), E5(0, 1, 'e', 5, 5), F5(1, 1, 'f', 5, 6),
	G5(2, 1, 'g', 5, 7), H5(3, 1, 'h', 5, 8), A4(-4, 0, 'a', 4, 1), B4(-3, 0, 'b', 4, 2), C4(-2, 0, 'c', 4, 3),
	D4(-1, 0, 'd', 4, 4), E4(0, 0, 'e', 4, 5), F4(1, 0, 'f', 4, 6), G4(2, 0, 'g', 4, 7), H4(3, 0, 'h', 4, 8),
	A3(-4, -1, 'a', 3, 1), B3(-3, -1, 'b', 3, 2), C3(-2, -1, 'c', 3, 3), D3(-1, -1, 'd', 3, 4), E3(0, -1, 'e', 3, 5),
	F3(1, -1, 'f', 3, 6), G3(2, -1, 'g', 3, 7), H3(3, -1, 'h', 3, 8), A2(-4, -2, 'a', 2, 1), B2(-3, -2, 'b', 2, 2),
	C2(-2, -2, 'c', 2, 3), D2(-1, -2, 'd', 2, 4), E2(0, -2, 'e', 2, 5), F2(1, -2, 'f', 2, 6), G2(2, -2, 'g', 2, 7),
	H2(3, -2, 'h', 2, 8), A1(-4, -3, 'a', 1, 1), B1(-3, -3, 'b', 1, 2), C1(-2, -3, 'c', 1, 3), D1(-1, -3, 'd', 1, 4),
	E1(0, -3, 'e', 1, 5), F1(1, -3, 'f', 1, 6), G1(2, -3, 'g', 1, 7), H1(3, -3, 'h', 1, 8);

	private final int x;
	private final int y;
	private final int row;
	private final char col;
	private final int colPos;

	private CasePosition(int x, int y, char col, int row, int colPos) {
		this.x = x;
		this.y = y;
		this.col = col;
		this.row = row;
		this.colPos = colPos;
	}

	public int getY() {
		return this.y;
	}

	public char getCol() {
		return this.col;
	}

	public int getRow() {
		return this.row;
	}

	public int getX() {
		return this.x;
	}

	public int getColPos() {
		return this.colPos;
	}

	public boolean isOnSameColumn(char value) {
		return this.col == value;
	}

	public boolean isOnSameRow(char value) {
		return this.row == Character.getNumericValue(value);
	}
}
