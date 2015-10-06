/**
 *
 */
package sims.module.surface;

 class CellType {

 private final ArrayList<CellProperty> properties;

 /**
 * Adds all cell properties to current cell
 */
 public CellType(CellProperty... properties) {

 this.properties = new ArrayList<CellProperty>();

 for (CellProperty property : properties) {
 this.properties.add(property);
 }

 }

 public boolean containsProperty(CellProperty prop) {

 return this.properties.contains(prop);

 }

 @Override
 public boolean equals(Object obj) {
 if (!(obj instanceof CellType)) {
 return false;
 } else if (obj == this) {
 return true;
 }

 CellType temp = (CellType) obj;

 if (temp.getProperties().size() != this.properties.size()) {
 return false;
 }

 for (int i = 0; i < this.properties.size(); i++) {

 if (!temp.getProperties().contains(this.properties.get(i))) {
 return false;
 }

 }

 return true;
 }

 /**
 * @return the isDoor
 */
 public ArrayList<CellProperty> getProperties() {
 return this.properties;
 }

 }