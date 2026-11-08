/**
 * Tugas Ujian Data Structures - Sistem Digital Perpustakaan
 */

// 1. STRUKTUR DATA NODE & LINKED LIST 

class NodeBuku {
    String idBuku;
    String judul;
    String penulis;
    NodeBuku next;

    public NodeBuku(String idBuku, String judul, String penulis) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.penulis = penulis;
        this.next = null;
    }
}

class CustomLinkedListBuku {
    private NodeBuku head;
    private int size;

    public CustomLinkedListBuku() {
        this.head = null;
        this.size = 0;
    }

    // Insert di awal (Head) - O(1)
    public void addFirst(String id, String judul, String penulis) {
        NodeBuku newNode = new NodeBuku(id, judul, penulis);
        newNode.next = head;
        head = newNode;
        size++;
    }

    // Insert di akhir (Tail) - O(n)
    public void addLast(String id, String judul, String penulis) {
        NodeBuku newNode = new NodeBuku(id, judul, penulis);
        if (head == null) {
            head = newNode;
        } else {
            NodeBuku temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
        size++;
    }

    // Delete berdasarkan ID Buku - O(n)
    public boolean removeById(String id) {
        if (head == null) return false;

        if (head.idBuku.equals(id)) {
            head = head.next;
            size--;
            return true;
        }

        NodeBuku current = head;
        while (current.next != null && !current.next.idBuku.equals(id)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
            size--;
            return true;
        }
        return false; // Tidak ditemukan
    }

    public void cetakDaftarBuku() {
        if (head == null) {
            System.out.println("   [Koleksi buku kosong]");
            return;
        }
        NodeBuku temp = head;
        int idx = 1;
        while (temp != null) {
            System.out.println("   " + idx + ". [" + temp.idBuku + "] " + temp.judul + " (Penulis: " + temp.penulis + ")");
            temp = temp.next;
            idx++;
        }
    }

    public int getSize() { return size; }
}



// 2. STRUKTUR DATA DYNAMIC ARRAY 


class DynamicArrayAnggota {
    private Anggota[] data;
    private int size;
    private int capacity;

    public DynamicArrayAnggota() {
        this.capacity = 2; // Kapasitas awal kecil untuk demonstrasi auto-resize
        this.data = new Anggota[capacity];
        this.size = 0;
    }

    // Double capacity saat penuh
    private void resize() {
        int newCapacity = capacity * 2;
        Anggota[] newData = new Anggota[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
        capacity = newCapacity;
    }

    public void add(Anggota anggota) {
        if (size == capacity) {
            resize();
        }
        data[size] = anggota;
        size++;
    }

    public Anggota get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Indeks melebihi batas!");
        }
        return data[index];
    }

    public int getSize() { return size; }
    public int getCapacity() { return capacity; }
}



// 3. ABSTRAKSI & COMPOSITION PERATURAN PEMINJAMAN


interface AturanPeminjaman {
    int getMaksimalPinjam();
    int getDurasiPinjamHari();
    String getDeskripsiPeran();
}

class AturanMahasiswa implements AturanPeminjaman {
    @Override public int getMaksimalPinjam() { return 3; }
    @Override public int getDurasiPinjamHari() { return 7; }
    @Override public String getDeskripsiPeran() { return "Mahasiswa"; }
}

class AturanDosen implements AturanPeminjaman {
    @Override public int getMaksimalPinjam() { return 10; }
    @Override public int getDurasiPinjamHari() { return 30; }
    @Override public String getDeskripsiPeran() { return "Dosen"; }
}

// Penambahan Jenis Anggota Baru "Alumni" Tanpa Mengubah Kode Lain
class AturanAlumni implements AturanPeminjaman {
    @Override public int getMaksimalPinjam() { return 2; }
    @Override public int getDurasiPinjamHari() { return 5; }
    @Override public String getDeskripsiPeran() { return "Alumni"; }
}

// Entity Class Anggota
class Anggota {
    private String idAnggota;
    private String nama;
    private AturanPeminjaman aturanPeminjaman; // Composition

    public Anggota(String idAnggota, String nama, AturanPeminjaman aturanPeminjaman) {
        this.idAnggota = idAnggota;
        this.nama = nama;
        this.aturanPeminjaman = aturanPeminjaman;
    }

    public String getIdAnggota() { return idAnggota; }
    public String getNama() { return nama; }
    
    public void setAturanPeminjaman(AturanPeminjaman aturanBaru) {
        this.aturanPeminjaman = aturanBaru;
    }

    public void tampilkanProfil() {
        System.out.println("ID: " + idAnggota + " | Nama: " + nama + 
                           " | Peran: " + aturanPeminjaman.getDeskripsiPeran() + 
                           " | Max Pinjam: " + aturanPeminjaman.getMaksimalPinjam() + " buku" +
                           " | Durasi: " + aturanPeminjaman.getDurasiPinjamHari() + " hari");
    }
}


// 4. MAIN PROGRAM 


public class MainPerpustakaan {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   DEMO SISTEM PERPUSTAKAAN - STRUKTUR DATA");
        System.out.println("==================================================\n");

        // 1: DYNAMIC ARRAY ANGGOTA 
        System.out.println("[1] Pengujian Dynamic Array Anggota (Auto-Resize)");
        DynamicArrayAnggota daftarAnggota = new DynamicArrayAnggota();

        System.out.println("Kapasitas awal array: " + daftarAnggota.getCapacity());
        
        daftarAnggota.add(new Anggota("MHS01", "Budi Santoso", new AturanMahasiswa()));
        daftarAnggota.add(new Anggota("DSN01", "Dr. Ir. Hendra", new AturanDosen()));
        System.out.println("Menambahkan 2 anggota. Jumlah: " + daftarAnggota.getSize() + ", Kapasitas: " + daftarAnggota.getCapacity());
        
        // Memicu auto-resize (size == capacity == 2)
        daftarAnggota.add(new Anggota("ALM01", "Siti Rahma", new AturanAlumni()));
        System.out.println("Menambahkan 1 anggota baru (Alumni). Size: " + daftarAnggota.getSize() + ", Kapasitas Baru: " + daftarAnggota.getCapacity());
        System.out.println();

        System.out.println("Daftar Seluruh Anggota Terdaftar:");
        for (int i = 0; i < daftarAnggota.getSize(); i++) {
            System.out.print(" -> ");
            daftarAnggota.get(i).tampilkanProfil();
        }
        System.out.println();


        // 2: LINKED LIST KOLEKSI BUKU
        System.out.println("[2] Pengujian Linked List Koleksi Buku");
        CustomLinkedListBuku koleksiBuku = new CustomLinkedListBuku();

        koleksiBuku.addLast("BK01", "Struktur Data & Algoritma", "Adam Drozdek");
        koleksiBuku.addLast("BK02", "Clean Code", "Robert C. Martin");
        koleksiBuku.addFirst("BK00", "Pengantar Ilmu Komputer", "Anonym");

        System.out.println("Daftar Buku Setelah Insertion:");
        koleksiBuku.cetakDaftarBuku();
        System.out.println("Total Koleksi: " + koleksiBuku.getSize() + " buku\n");

        System.out.println("Menghapus Buku BK02 (Clean Code)...");
        boolean isDeleted = koleksiBuku.removeById("BK02");
        System.out.println("Status Penghapusan: " + (isDeleted ? "Berhasil" : "Gagal"));
        
        System.out.println("\nDaftar Buku Terkini:");
        koleksiBuku.cetakDaftarBuku();
        System.out.println("Total Koleksi Saat Ini: " + koleksiBuku.getSize() + " buku");
        System.out.println("\n==================================================");
    }
}