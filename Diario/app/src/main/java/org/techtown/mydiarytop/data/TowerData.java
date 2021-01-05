package org.techtown.mydiarytop.data;

public class TowerData {
    String towerName;
    Integer towerCount;
    Integer towerImage;
    Integer furnitureCount;
    Integer furnitureImage;

    public TowerData(String towerName, Integer towerCount, Integer towerImage, Integer furnitureCount, Integer furnitureImage) {
        this.towerName = towerName;
        this.towerCount = towerCount;
        this.towerImage = towerImage;
        this.furnitureCount = furnitureCount;
        this.furnitureImage = furnitureImage;
    }

    public String getTowerName() {
        return towerName;
    }

    public void setTowerName(String towerName) {
        this.towerName = towerName;
    }

    public Integer getTowerCount() {
        return towerCount;
    }

    public void setTowerCount(Integer towerCount) {
        this.towerCount = towerCount;
    }

    public Integer getTowerImage() {
        return towerImage;
    }

    public void setTowerImage(Integer towerImage) {
        this.towerImage = towerImage;
    }

    public Integer getFurnitureCount() {
        return furnitureCount;
    }

    public void setFurnitureCount(Integer furnitureCount) {
        this.furnitureCount = furnitureCount;
    }

    public Integer getFurnitureImage() {
        return furnitureImage;
    }

    public void setFurnitureImage(Integer furnitureImage) {
        this.furnitureImage = furnitureImage;
    }
}
