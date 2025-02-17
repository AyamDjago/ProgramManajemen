# Aplikasi Manajemen Inventaris

Proyek ini adalah aplikasi sederhana untuk Manajemen Inventaris yang dikembangkan menggunakan Java Swing untuk antarmuka pengguna grafis (GUI) dan SQLite sebagai basis data. Aplikasi ini memungkinkan pengguna melakukan operasi CRUD (Create, Read, Update, Delete) dasar pada basis data inventaris. Aplikasi ini dirancang untuk mengelola item inventaris seperti produk atau persediaan.



 Fitur

1. Tambah Item: Menambahkan item baru ke dalam inventaris.
2. Perbarui Item: Memodifikasi item yang sudah ada dalam inventaris.
3. Hapus Item: Menghapus item dari inventaris.
4. Lihat Item: Menampilkan semua item dalam inventaris menggunakan tabel.



Struktur Proyek

Kelas

1. InventoryCRUD: Mengelola semua operasi yang berhubungan dengan basis data, termasuk pembuatan tabel, penambahan, pembaruan, penghapusan, dan pengambilan item.
2. InventoryGUI: Menyediakan antarmuka pengguna grafis untuk berinteraksi dengan sistem inventaris.

---

 Cara Kerja

 Basis Data
- Aplikasi ini menggunakan basis data SQLite (`inventory.db`) untuk menyimpan data inventaris.
- Sebuah tabel bernama `inventory` dibuat dengan kolom berikut:
  - `id`: Kunci utama, ditambahkan secara otomatis.
  - `name`: Nama item inventaris.
  - `quantity`: Jumlah item.
  - `description`: Deskripsi item.
  - `created_at`: Waktu saat item ditambahkan.
  - `updated_at`: Waktu saat item terakhir diperbarui.

Komponen GUI
- Kolom Input:
  - `ID`: Untuk menentukan item yang akan diperbarui atau dihapus.
  - `Name`: Untuk nama item.
  - `Quantity`: Untuk jumlah item.
  - `Description`: Untuk detail tambahan tentang item.
- Tombol:
  - `Add Item`: Menambahkan item baru ke inventaris.
  - `Update Item`: Memperbarui item yang sudah ada berdasarkan ID-nya.
  - `Delete Item`: Menghapus item berdasarkan ID-nya.
- Tabel: Menampilkan daftar item dalam inventaris.

---

 Persyaratan

1. Java: Pastikan Anda memiliki JDK 8 atau yang lebih baru.
2. SQLite JDBC Driver: Disertakan dalam proyek untuk konektivitas basis data.

---

 Cara Menjalankan

1. Clone atau unduh file proyek.
2. Kompilasi file Java menggunakan:
   ```bash
   javac InventoryCRUD.java InventoryGUI.java
   ```
3. Jalankan aplikasi menggunakan:
   ```bash
   java InventoryGUI
   ```
4. Berinteraksilah dengan aplikasi melalui GUI untuk mengelola item inventaris.

---

 Penggunaan

1. Menambahkan Item:
   - Masukkan nama, jumlah, dan deskripsi item.
   - Klik tombol "Add Item".
2. Memperbarui Item:
   - Masukkan ID item yang akan diperbarui di kolom `ID`.
   - Modifikasi kolom nama, jumlah, atau deskripsi sesuai kebutuhan.
   - Klik tombol "Update Item".
3. Menghapus Item:
   - Masukkan ID item yang akan dihapus di kolom `ID`.
   - Klik tombol "Delete Item".
4. Melihat Item:
   - Tabel secara otomatis diperbarui dengan data inventaris terbaru setelah setiap operasi.

---

Penanganan Kesalahan

- **Validasi Jumlah**: Memastikan kolom jumlah berisi angka yang valid.
- **Validasi ID**: Memastikan kolom ID berisi angka yang valid untuk operasi pembaruan atau penghapusan.
- **Kesalahan Basis Data**: Setiap kesalahan terkait basis data ditampilkan dalam kotak dialog.

---

Peningkatan dan Pekerjaan Masa Depan

1. Menambahkan fungsi pencarian untuk memfilter item dalam inventaris.
2. Menerapkan pengurutan dan paginasi untuk menangani dataset yang besar dengan lebih baik.
3. Meningkatkan GUI untuk pengalaman pengguna yang lebih baik.
4. Menambahkan autentikasi untuk akses yang lebih aman.
5. Mengekspor data inventaris ke CSV atau Excel.

---

 Lisensi

Proyek ini bersifat open-source dan bebas digunakan.

