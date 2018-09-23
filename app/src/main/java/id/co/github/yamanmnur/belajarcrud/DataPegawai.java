package id.co.github.yamanmnur.belajarcrud;

public class DataPegawai {
    int idPegawai;
    String namaPegawai;
    String posisiPegawai;
    int gajihPegawai;
    public DataPegawai(int idPegawai, String namaPegawai, String posisiPegawai, int gajihPegawai){
        this.idPegawai = idPegawai;
        this.namaPegawai = namaPegawai;
        this.posisiPegawai = posisiPegawai;
        this.gajihPegawai = gajihPegawai;
    }

    public void setIdPegawai(int idPegawai) {
        this.idPegawai = idPegawai;
    }

    public int getIdPegawai() {
        return idPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setPosisiPegawai(String posisiPegawai) {
        this.posisiPegawai = posisiPegawai;
    }

    public String getPosisiPegawai() {
        return posisiPegawai;
    }

    public void setGajihPegawai(int gajihPegawai) {
        this.gajihPegawai = gajihPegawai;
    }



}

