package me.veeraya.ISO8583.field;

//ในวิกิ บอกว่า การเข้ารหัสมีหลายแบบ
public enum Encoding {
	ASC, BCD
    /*BCD = Binary Coded Decimal แปลงที่ละตัวที่เจอ 15 => 0001 0101:BCD
    *										 => 1111 : binary
    */
	// ASC==ASCII
}
