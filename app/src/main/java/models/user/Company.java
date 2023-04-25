package models.user;

public class Company {
   private Address address;
   private String department;
   private String name;
   private String title;

   public Company(Address address, String department, String name, String title) {
      this.address = address;
      this.department = department;
      this.name = name;
      this.title = title;
   }

   public Address getAddress() {
      return address;
   }

   public void setAddress(Address address) {
      this.address = address;
   }

   public String getDepartment() {
      return department;
   }

   public void setDepartment(String department) {
      this.department = department;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   @Override
   public String toString() {
      return "Company{" +
              "address=" + address +
              ", department='" + department + '\'' +
              ", name='" + name + '\'' +
              ", title='" + title + '\'' +
              '}';
   }
}
