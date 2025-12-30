package com.example.luxevista_resort.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.example.luxevista_resort.model.Booking;
import com.example.luxevista_resort.model.Guest;
import com.example.luxevista_resort.model.Room;
import com.example.luxevista_resort.model.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "LuxeVistaFinal.db";
    public static final int DATABASE_VERSION = 5;

    // Guests Table
    public static final String GUESTS_TABLE = "guests";
    public static final String GUEST_ID = "guest_id";
    public static final String GUEST_FIRST_NAME = "first_name";
    public static final String GUEST_LAST_NAME = "last_name";
    public static final String GUEST_EMAIL = "email";
    public static final String GUEST_PASSWORD = "password";
    public static final String GUEST_PHONE = "phone";
    public static final String GUEST_ADDRESS = "address";

    // Rooms Table
    public static final String ROOMS_TABLE = "rooms";
    public static final String ROOM_ID = "room_id";
    public static final String ROOM_TYPE = "room_type";
    public static final String ROOM_DESCRIPTION = "description";
    public static final String ROOM_PRICE = "price_per_night";
    public static final String ROOM_MAX_ADULTS = "max_adults";
    public static final String ROOM_MAX_CHILDREN = "max_children";
    public static final String ROOM_IMAGE_URL = "image_url";

    // Services Table
    public static final String SERVICES_TABLE = "services";
    public static final String SERVICE_ID = "service_id";
    public static final String SERVICE_NAME = "service_name";
    public static final String SERVICE_CATEGORY = "service_category";
    public static final String SERVICE_DESCRIPTION = "description";
    public static final String SERVICE_PRICE = "price";
    public static final String SERVICE_DURATION = "duration";
    public static final String SERVICE_IMAGE_URL = "image_url";

    // Bookings Table
    public static final String BOOKINGS_TABLE = "bookings";
    public static final String BOOKING_ID = "booking_id";
    public static final String BOOKING_GUEST_ID = "guest_id";
    public static final String BOOKING_TYPE = "booking_type";
    public static final String BOOKING_ITEM_ID = "item_id";
    public static final String BOOKING_CHECK_IN_DATE = "check_in_date";
    public static final String BOOKING_CHECK_OUT_DATE = "check_out_date";
    public static final String BOOKING_DETAILS = "details";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createGuestsTable = "CREATE TABLE " + GUESTS_TABLE + " (" + GUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + GUEST_FIRST_NAME + " TEXT, " + GUEST_LAST_NAME + " TEXT, " + GUEST_EMAIL + " TEXT UNIQUE, " + GUEST_PASSWORD + " TEXT, " + GUEST_PHONE + " TEXT, " + GUEST_ADDRESS + " TEXT)";
        db.execSQL(createGuestsTable);

        String createRoomsTable = "CREATE TABLE " + ROOMS_TABLE + " (" + ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ROOM_TYPE + " TEXT, " + ROOM_DESCRIPTION + " TEXT, " + ROOM_PRICE + " REAL, " + ROOM_MAX_ADULTS + " INTEGER, " + ROOM_MAX_CHILDREN + " INTEGER, " + ROOM_IMAGE_URL + " TEXT)";
        db.execSQL(createRoomsTable);

        String createServicesTable = "CREATE TABLE " + SERVICES_TABLE + " (" + SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SERVICE_NAME + " TEXT, " + SERVICE_CATEGORY + " TEXT, " + SERVICE_DESCRIPTION + " TEXT, " + SERVICE_PRICE + " REAL, " + SERVICE_DURATION + " INTEGER, " + SERVICE_IMAGE_URL + " TEXT)";
        db.execSQL(createServicesTable);

        String createBookingsTable = "CREATE TABLE " + BOOKINGS_TABLE + " (" +
                BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BOOKING_GUEST_ID + " INTEGER NOT NULL, " +
                BOOKING_TYPE + " TEXT NOT NULL, " +
                BOOKING_ITEM_ID + " INTEGER NOT NULL, " +
                BOOKING_CHECK_IN_DATE + " TEXT, " +
                BOOKING_CHECK_OUT_DATE + " TEXT, " +
                BOOKING_DETAILS + " TEXT, " +
                "FOREIGN KEY(" + BOOKING_GUEST_ID + ") REFERENCES " + GUESTS_TABLE + "(" + GUEST_ID + "))";
        db.execSQL(createBookingsTable);

        populateInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BOOKINGS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SERVICES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ROOMS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GUESTS_TABLE);
        onCreate(db);
    }

    private void populateInitialData(SQLiteDatabase db) {
        addRoom(db, "Oceanfront Grand Suite", "85 m² | Direct oceanfront view, private balcony, king bed, separate living area.", 450.0, 2, 1, "oceanfront_grand_suite");
        addRoom(db, "Horizon Deluxe Room", "45 m² | Partial ocean view with balcony, king or twin beds.", 280.0, 2, 0, "horizon_deluxe_room");
        addRoom(db, "Tropical Pool Access Room", "40 m² | Direct access to main pool, private patio, outdoor shower.", 220.0, 2, 0, "tropical_pool_access");
        addRoom(db, "Luxe Garden Villa", "120 m² | Private plunge pool, outdoor living area, butler service.", 600.0, 2, 2, "luxe_garden_villa");
        addRoom(db, "LuxeVista Presidential Suite", "200 m² | 270-degree ocean and garden view, two bedrooms, private infinity pool.", 1200.0, 4, 0, "presidential_suite");

        addService(db, "Coral Reef Massage", "Spa", "Deep tissue massage using warm sea stones and marine mineral oil.", 150.0, 90, "coral_reef_massage_placeholder");
        addService(db, "Tidal Wave Therapy", "Spa", "Full-body rhythmic massage mimicking ocean waves.", 200.0, 120,"tidal_wave_therapy_placeholder");
        addService(db, "Seashell Reflexology", "Spa", "Foot massage using heated seashells and pressure points.", 90.0, 60,"seashell_reflexology_placeholder");
        addService(db, "Coconut & Lime Body Glow", "Spa", "Exfoliation with fresh coconut and lime followed by moisturizing wrap.", 120.0, 75,"coconut_lime_body_glow_placeholder");
        addService(db, "Tropical Flower Bath", "Spa", "Immersion in floral-infused waters with champagne service.", 80.0, 45,"tropical_flower_bath_placeholder");
        addService(db, "Monsoon Rain Shower Massage", "Spa", "Water-based massage under a warm rainfall shower.", 160.0, 90,"monsoon_rain_shower_placeholder");
        addService(db, "Ayurvedic Abhyanga", "Spa", "Traditional four-handed synchronized massage with medicated oils.", 180.0, 120,"ayurvedic_abhyanga_placeholder");
        addService(db, "Spice Garden Scrub", "Spa", "Exfoliation with cinnamon, cardamom, and local spices.", 100.0, 60,"spice_garden_scrub_placeholder");
        addService(db, "Ceylon Tea Detox Wrap", "Spa", "Anti-oxidant rich tea leaf body wrap.", 140.0, 90,"ceylon_tea_detox_wrap_placeholder");

        String azureDesc = "Cuisine: Contemporary International with Sri Lankan influences\nAmbiance: Beachfront fine dining with sunset views\nSpecialty: \"Catch of the Day\" - fresh local seafood";
        String sapphireDesc = "Cuisine: Modern European\nAmbiance: Sophisticated indoor dining with crystal decor\nSpecialty: Chef's 7-course tasting menu";
        String spiceDesc = "Cuisine: Authentic Sri Lankan & Asian Fusion\nAmbiance: Open-air pavilion with tropical gardens\nSpecialty: Traditional rice and curry banquet";
        String privateDiningDesc = "Location: Secluded beach coves\nSetting: Personal butler, torch-lit setup, private chef\nIdeal For: Anniversaries, proposals";

        addService(db, "Azure Horizon", "Dining", azureDesc, 95.0, 120, "azure_horizon");
        addService(db, "The Sapphire Room", "Dining", sapphireDesc, 180.0, 180, "sapphire_room");
        addService(db, "Spice Garden", "Dining", spiceDesc, 75.0, 90, "spice_garden");
        addService(db, "Private Beach Dining", "Dining", privateDiningDesc, 250.0, 180, "private_beach_dining");

        addService(db, "Ocean View Premium Cabana", "Cabanas", "King-size daybed, mini-fridge, and dedicated butler.", 120.0, 540,"cabana_ocean");
        addService(db, "Garden Cabana", "Cabanas", "Double daybed with privacy curtains and pool attendant service.", 80.0, 540, "cabana_garden");
        addService(db, "Family Cabana", "Cabanas", "Two double daybeds, extra storage, and kid-safe area.", 150.0, 540, "cabana_family");

        addService(db, "Sunrise Beach Meditation", "Tours", "Guided meditation at our private sunrise beach cove. Complimentary for guests.", 0.0, 60, "sunrise_meditation");
        addService(db, "Turtle Watch Expedition", "Tours", "Seasonal night expedition with a marine conservation specialist.", 45.0, 120, "tour_turtle");
        addService(db, "Coral Garden Snorkel Tour", "Tours", "Guided snorkel tour of the protected reef within resort boundaries.", 65.0, 150, "coral_snorkel");
        addService(db, "Local Fishing Village Tour", "Tours", "Visit a traditional village, meet local artisans, and sample authentic snacks.", 55.0, 180, "fishing_village");
    }

    private void addRoom(SQLiteDatabase db, String type, String desc, double price, int maxAdults, int maxChildren, String imageUrl) {
        ContentValues cv = new ContentValues();
        cv.put(ROOM_TYPE, type);
        cv.put(ROOM_DESCRIPTION, desc);
        cv.put(ROOM_PRICE, price);
        cv.put(ROOM_MAX_ADULTS, maxAdults);
        cv.put(ROOM_MAX_CHILDREN, maxChildren);
        cv.put(ROOM_IMAGE_URL, imageUrl);
        db.insert(ROOMS_TABLE, null, cv);
    }

    private void addService(SQLiteDatabase db, String name, String category, String desc, double price, int duration, String imageUrl) {
        ContentValues cv = new ContentValues();
        cv.put(SERVICE_NAME, name);
        cv.put(SERVICE_CATEGORY, category);
        cv.put(SERVICE_DESCRIPTION, desc);
        cv.put(SERVICE_PRICE, price);
        cv.put(SERVICE_DURATION, duration);
        cv.put(SERVICE_IMAGE_URL, imageUrl);
        db.insert(SERVICES_TABLE, null, cv);
    }

    public boolean addGuest(String firstName, String lastName, String email, String password, String phone, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GUEST_FIRST_NAME, firstName);
        cv.put(GUEST_LAST_NAME, lastName);
        cv.put(GUEST_EMAIL, email);
        cv.put(GUEST_PASSWORD, password);
        cv.put(GUEST_PHONE, phone);
        cv.put(GUEST_ADDRESS, address);
        long result = db.insert(GUESTS_TABLE, null, cv);
        db.close();
        return result != -1;
    }

    @SuppressLint("Range")
    public Guest getGuestByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(GUESTS_TABLE, null, GUEST_EMAIL + "=?", new String[]{email}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Guest guest = new Guest(
                    cursor.getInt(cursor.getColumnIndex(GUEST_ID)),
                    cursor.getString(cursor.getColumnIndex(GUEST_FIRST_NAME)),
                    cursor.getString(cursor.getColumnIndex(GUEST_LAST_NAME)),
                    cursor.getString(cursor.getColumnIndex(GUEST_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(GUEST_PHONE)),
                    cursor.getString(cursor.getColumnIndex(GUEST_ADDRESS))
            );
            cursor.close();
            return guest;
        }
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    public boolean checkGuest(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(GUESTS_TABLE, new String[]{GUEST_ID}, GUEST_EMAIL + " = ? AND " + GUEST_PASSWORD + " = ?", new String[]{email, password}, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(GUESTS_TABLE, new String[]{GUEST_ID}, GUEST_EMAIL + "=?", new String[]{email}, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    public long addBooking(int guestId, String type, int itemId, String checkIn, String checkOut, String details) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BOOKING_GUEST_ID, guestId);
        cv.put(BOOKING_TYPE, type);
        cv.put(BOOKING_ITEM_ID, itemId);
        cv.put(BOOKING_CHECK_IN_DATE, checkIn);
        cv.put(BOOKING_CHECK_OUT_DATE, checkOut);
        cv.put(BOOKING_DETAILS, details);
        long id = db.insert(BOOKINGS_TABLE, null, cv);
        db.close();
        return id;
    }

    private String getTodayDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    @SuppressLint("Range")
    public Booking getUpcomingBooking(int guestId) {
        Booking booking = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String today = getTodayDateString();

        String query = "SELECT b.*, " +
                "CASE WHEN b." + BOOKING_TYPE + " = 'Room' THEN r." + ROOM_TYPE + " ELSE s." + SERVICE_NAME + " END as itemName, " +
                "CASE WHEN b." + BOOKING_TYPE + " = 'Room' THEN r." + ROOM_IMAGE_URL + " ELSE s." + SERVICE_IMAGE_URL + " END as itemImage " +
                "FROM " + BOOKINGS_TABLE + " b " +
                "LEFT JOIN " + ROOMS_TABLE + " r ON b." + BOOKING_ITEM_ID + " = r." + ROOM_ID + " AND b." + BOOKING_TYPE + " = 'Room' " +
                "LEFT JOIN " + SERVICES_TABLE + " s ON b." + BOOKING_ITEM_ID + " = s." + SERVICE_ID + " AND b." + BOOKING_TYPE + " != 'Room' " +
                "WHERE b." + BOOKING_GUEST_ID + " = ? AND b." + BOOKING_CHECK_IN_DATE + " >= ? " +
                "ORDER BY b." + BOOKING_CHECK_IN_DATE + " ASC LIMIT 1";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(guestId), today});

        if (cursor.moveToFirst()) {
            String dateInfo;
            String checkOut = cursor.getString(cursor.getColumnIndex(BOOKING_CHECK_OUT_DATE));
            String checkIn = cursor.getString(cursor.getColumnIndex(BOOKING_CHECK_IN_DATE));
            if (checkOut != null && !checkOut.isEmpty()) {
                dateInfo = checkIn + " to " + checkOut;
            } else {
                dateInfo = checkIn;
            }

            booking = new Booking(
                    cursor.getInt(cursor.getColumnIndex(BOOKING_ID)),
                    cursor.getInt(cursor.getColumnIndex(BOOKING_GUEST_ID)),
                    cursor.getString(cursor.getColumnIndex(BOOKING_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(BOOKING_ITEM_ID)),
                    dateInfo,
                    cursor.getString(cursor.getColumnIndex(BOOKING_DETAILS)),
                    cursor.getString(cursor.getColumnIndex("itemName")),
                    cursor.getString(cursor.getColumnIndex("itemImage"))
            );
        }
        cursor.close();
        db.close();
        return booking;
    }

    @SuppressLint("Range")
    public List<Booking> getGuestBookingsWithImage(int guestId) {
        List<Booking> bookingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT b.*, " +
                "CASE WHEN b." + BOOKING_TYPE + " = 'Room' THEN r." + ROOM_TYPE + " ELSE s." + SERVICE_NAME + " END as itemName, " +
                "CASE WHEN b." + BOOKING_TYPE + " = 'Room' THEN r." + ROOM_IMAGE_URL + " ELSE s." + SERVICE_IMAGE_URL + " END as itemImage " +
                "FROM " + BOOKINGS_TABLE + " b " +
                "LEFT JOIN " + ROOMS_TABLE + " r ON b." + BOOKING_ITEM_ID + " = r." + ROOM_ID + " AND b." + BOOKING_TYPE + " = 'Room' " +
                "LEFT JOIN " + SERVICES_TABLE + " s ON b." + BOOKING_ITEM_ID + " = s." + SERVICE_ID + " AND b." + BOOKING_TYPE + " != 'Room' " +
                "WHERE b." + BOOKING_GUEST_ID + " = ? ORDER BY b." + BOOKING_CHECK_IN_DATE + " ASC";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(guestId)});

        if (cursor.moveToFirst()) {
            do {
                String dateInfo;
                String checkOut = cursor.getString(cursor.getColumnIndex(BOOKING_CHECK_OUT_DATE));
                String checkIn = cursor.getString(cursor.getColumnIndex(BOOKING_CHECK_IN_DATE));
                if (checkOut != null && !checkOut.isEmpty()) {
                    dateInfo = checkIn + " to " + checkOut;
                } else {
                    dateInfo = checkIn;
                }

                bookingList.add(new Booking(
                        cursor.getInt(cursor.getColumnIndex(BOOKING_ID)),
                        cursor.getInt(cursor.getColumnIndex(BOOKING_GUEST_ID)),
                        cursor.getString(cursor.getColumnIndex(BOOKING_TYPE)),
                        cursor.getInt(cursor.getColumnIndex(BOOKING_ITEM_ID)),
                        dateInfo,
                        cursor.getString(cursor.getColumnIndex(BOOKING_DETAILS)),
                        cursor.getString(cursor.getColumnIndex("itemName")),
                        cursor.getString(cursor.getColumnIndex("itemImage"))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookingList;
    }

    @SuppressLint("Range")
    public List<Object> searchRoomsAndServices(String query) {
        List<Object> results = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String searchQuery = "%" + query + "%";

        // Search Rooms
        Cursor roomCursor = db.query(ROOMS_TABLE, null,
                ROOM_TYPE + " LIKE ? OR " + ROOM_DESCRIPTION + " LIKE ?",
                new String[]{searchQuery, searchQuery}, null, null, ROOM_PRICE);
        if (roomCursor.moveToFirst()) {
            do {
                results.add(new Room(roomCursor.getInt(roomCursor.getColumnIndex(ROOM_ID)), roomCursor.getString(roomCursor.getColumnIndex(ROOM_TYPE)), roomCursor.getString(roomCursor.getColumnIndex(ROOM_DESCRIPTION)), roomCursor.getDouble(roomCursor.getColumnIndex(ROOM_PRICE)), roomCursor.getInt(roomCursor.getColumnIndex(ROOM_MAX_ADULTS)), roomCursor.getInt(roomCursor.getColumnIndex(ROOM_MAX_CHILDREN)), roomCursor.getString(roomCursor.getColumnIndex(ROOM_IMAGE_URL))));
            } while (roomCursor.moveToNext());
        }
        roomCursor.close();

        // Search Services
        Cursor serviceCursor = db.query(SERVICES_TABLE, null,
                SERVICE_NAME + " LIKE ? OR " + SERVICE_DESCRIPTION + " LIKE ?",
                new String[]{searchQuery, searchQuery}, null, null, SERVICE_NAME);
        if (serviceCursor.moveToFirst()) {
            do {
                results.add(new Service(serviceCursor.getInt(serviceCursor.getColumnIndexOrThrow(SERVICE_ID)), serviceCursor.getString(serviceCursor.getColumnIndexOrThrow(SERVICE_NAME)), serviceCursor.getString(serviceCursor.getColumnIndexOrThrow(SERVICE_CATEGORY)), serviceCursor.getString(serviceCursor.getColumnIndexOrThrow(SERVICE_DESCRIPTION)), serviceCursor.getDouble(serviceCursor.getColumnIndexOrThrow(SERVICE_PRICE)), serviceCursor.getInt(serviceCursor.getColumnIndexOrThrow(SERVICE_DURATION)), serviceCursor.getString(serviceCursor.getColumnIndexOrThrow(SERVICE_IMAGE_URL))));
            } while (serviceCursor.moveToNext());
        }
        serviceCursor.close();

        db.close();
        return results;
    }

    public boolean deleteBooking(int bookingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(BOOKINGS_TABLE, BOOKING_ID + "=?", new String[]{String.valueOf(bookingId)});
        db.close();
        return result > 0;
    }

    @SuppressLint("Range")
    public List<Room> getAvailableRooms(String checkIn, String checkOut, int numGuests) {
        List<Room> roomList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        if (checkIn == null || checkOut == null) {
            Cursor cursor = db.query(ROOMS_TABLE, null, "(" + ROOM_MAX_ADULTS + " + " + ROOM_MAX_CHILDREN + ") >= ?", new String[]{String.valueOf(numGuests)}, null, null, ROOM_PRICE);
            if (cursor.moveToFirst()) {
                do {
                    roomList.add(new Room(cursor.getInt(cursor.getColumnIndex(ROOM_ID)), cursor.getString(cursor.getColumnIndex(ROOM_TYPE)), cursor.getString(cursor.getColumnIndex(ROOM_DESCRIPTION)), cursor.getDouble(cursor.getColumnIndex(ROOM_PRICE)), cursor.getInt(cursor.getColumnIndex(ROOM_MAX_ADULTS)), cursor.getInt(cursor.getColumnIndex(ROOM_MAX_CHILDREN)), cursor.getString(cursor.getColumnIndex(ROOM_IMAGE_URL))));
                } while (cursor.moveToNext());
            }
            cursor.close();
            return roomList;
        }

        String subQuery = "SELECT " + BOOKING_ITEM_ID + " FROM " + BOOKINGS_TABLE +
                " WHERE " + BOOKING_TYPE + " = 'Room' AND " +
                "((" + BOOKING_CHECK_IN_DATE + " < '" + checkOut + "') AND (" + BOOKING_CHECK_OUT_DATE + " > '" + checkIn + "'))";
        String query = "SELECT * FROM " + ROOMS_TABLE +
                " WHERE (" + ROOM_MAX_ADULTS + " + " + ROOM_MAX_CHILDREN + ") >= " + numGuests +
                " AND " + ROOM_ID + " NOT IN (" + subQuery + ")" +
                " ORDER BY " + ROOM_PRICE;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                roomList.add(new Room(cursor.getInt(cursor.getColumnIndex(ROOM_ID)), cursor.getString(cursor.getColumnIndex(ROOM_TYPE)), cursor.getString(cursor.getColumnIndex(ROOM_DESCRIPTION)), cursor.getDouble(cursor.getColumnIndex(ROOM_PRICE)), cursor.getInt(cursor.getColumnIndex(ROOM_MAX_ADULTS)), cursor.getInt(cursor.getColumnIndex(ROOM_MAX_CHILDREN)), cursor.getString(cursor.getColumnIndex(ROOM_IMAGE_URL))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return roomList;
    }

    @SuppressLint("Range")
    public List<Service> getAllServices() {
        List<Service> serviceList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SERVICES_TABLE, null);
        if (cursor.moveToFirst()) {
            do {
                serviceList.add(new Service(cursor.getInt(cursor.getColumnIndexOrThrow(SERVICE_ID)), cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_NAME)), cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_CATEGORY)), cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_DESCRIPTION)), cursor.getDouble(cursor.getColumnIndexOrThrow(SERVICE_PRICE)), cursor.getInt(cursor.getColumnIndexOrThrow(SERVICE_DURATION)), cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_IMAGE_URL))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return serviceList;
    }

    @SuppressLint("Range")
    public List<Service> getServicesByCategory(String category) {
        List<Service> serviceList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(SERVICES_TABLE, null, SERVICE_CATEGORY + " = ?", new String[]{category}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                serviceList.add(new Service(cursor.getInt(cursor.getColumnIndexOrThrow(SERVICE_ID)), cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_NAME)), cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_CATEGORY)), cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_DESCRIPTION)), cursor.getDouble(cursor.getColumnIndexOrThrow(SERVICE_PRICE)), cursor.getInt(cursor.getColumnIndexOrThrow(SERVICE_DURATION)), cursor.getString(cursor.getColumnIndexOrThrow(SERVICE_IMAGE_URL))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return serviceList;
    }
}

