package me.veeraya.ISO8583.field;

public enum ChackLenght {
	// สถานะถ้า lenght ไม่เท่ากับ lenght ที่กำหนด
	ZERO_APPEND, // จะเอาไว้เพิ่ม 0 ข้างหลัง
    ZERO_PREPEND,// จะเอาไว้เพิ่ม 0 ข้างหน้า
    SPACE_APPEND,// จะเอาไว้เพิ่ม " " ข้างหน้า 
    SPACE_PREPEND// จะเอาไว้เพิ่ม " " ข้างหลัง
}
