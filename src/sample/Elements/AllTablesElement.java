package sample.Elements;

public class AllTablesElement {
    //рефы
    private String ProductRef;
    private String PositionRef;
    private String ProductionRef;
    private String RegionRef;

    //Article
    private String ArticleName;
    private String WordCount;
    private String ArticleTheme;

    //DistributionPoint
    private String PointId;
    private String PointAdress;
    private String PointName;

    //Employee
    private String EmployeeName;
    private String EmployeeId;
    private String Age;
    private String Birthday;

    //Machine
    private String MachineId;
    private String MachineName;
    private String MachineType;
    private String PlantRef;
    private String DateOfPurchase;

    //Plant
    private String PlantId;
    private String PlantName;

    //Position
    private String PositionId;
    private String PositionName;
    private String Department;
    private String Salary;

    //Product
    private String ProductName;
    private String ProductCirculation;
    private String ProductPrice;
    private String NumberOfPages;

    //Production
    private String ProductionId;
    private String ReleasedProducts;
    private String DefectiveProducts;
    private String ProductionDate;

    //ProductType
    private String ProductTypeName;
    private String AgeLimit;
    private String Audience;
    private String Tipe_id;

    //Provider
    private String ProviderId;
    private String ProviderName;
    private String ProviderPrice;
    private String Resource;

    //Region
    private String RegionId;
    private String RegionName;

    public String getProductRef() {
        return ProductRef;
    }

    public String getPositionRef() {
        return PositionRef;
    }

    public String getProductionRef() {
        return ProductionRef;
    }

    public String getRegionRef() {
        return RegionRef;
    }

    public String getArticleName() {
        return ArticleName;
    }

    public String getWordCount() {
        return WordCount;
    }

    public String getArticleTheme() {
        return ArticleTheme;
    }

    public String getPointId() {
        return PointId;
    }

    public String getPointAdress() {
        return PointAdress;
    }

    public String getPointName() {
        return PointName;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public String getEmployeeId() {
        return EmployeeId;
    }

    public String getAge() {
        return Age;
    }

    public String getBirthday() {
        return Birthday;
    }

    public String getMachineId() {
        return MachineId;
    }

    public String getMachineName() {
        return MachineName;
    }

    public String getMachineType() {
        return MachineType;
    }

    public String getPlantRef() {
        return PlantRef;
    }

    public String getDateOfPurchase() {
        return DateOfPurchase;
    }

    public String getPlantId() {
        return PlantId;
    }

    public String getPlantName() {
        return PlantName;
    }

    public String getPositionId() {
        return PositionId;
    }

    public String getPositionName() {
        return PositionName;
    }

    public String getDepartment() {
        return Department;
    }

    public String getSalary() {
        return Salary;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getProductCirculation() {
        return ProductCirculation;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public String getNumberOfPages() {
        return NumberOfPages;
    }

    public String getProductionId() {
        return ProductionId;
    }

    public String getReleasedProducts() {
        return ReleasedProducts;
    }

    public String getDefectiveProducts() {
        return DefectiveProducts;
    }

    public String getProductionDate() {
        return ProductionDate;
    }

    public String getProductTypeName() {
        return ProductTypeName;
    }

    public String getAgeLimit() {
        return AgeLimit;
    }

    public String getAudience() {
        return Audience;
    }

    public String getTipe_id() {
        return Tipe_id;
    }

    public String getProviderId() {
        return ProviderId;
    }

    public String getProviderName() {
        return ProviderName;
    }

    public String getProviderPrice() {
        return ProviderPrice;
    }

    public String getResource() {
        return Resource;
    }

    public String getRegionId() {
        return RegionId;
    }

    public String getRegionName() {
        return RegionName;
    }

    public void createDistributionPoint(String pointId, String pointAdress, String pointName, String productRef) {
        this.PointAdress = pointAdress;
        this.PointId = pointId;
        this.PointName = pointName;
        this.ProductRef = productRef;
    }

    public void createArticle(String articleName, String wordCount, String articleTheme, String productRef) {
        this.ArticleName = articleName;
        this.WordCount = wordCount;
        this.ArticleTheme = articleTheme;
        this.ProductRef = productRef;
    }

    public void createEmployee(String ProductionRef, String EmployeeName, String EmployeeId,
                               String Age, String PositionRef, String Birthday){
        this.Age = Age;
        this.ProductionRef = ProductionRef;
        this.EmployeeName = EmployeeName;
        this.EmployeeId = EmployeeId;
        this.PositionRef = PositionRef;
        this.Birthday = Birthday;
    }

    public void createMachine(String MachineId, String MachineName, String MachineType, String PlantRef, String DatefPurchase){
        this.MachineId = MachineId;
        this.MachineName = MachineName;
        this.MachineType = MachineType;
        this.PlantRef = PlantRef;
        this.DateOfPurchase = DatefPurchase;
    }

    public void createPlant(String PlantId, String PlantName, String ProductionRef, String RegionRef){
        this.RegionRef = RegionRef;
        this.PlantId = PlantId;
        this.PlantName = PlantName;
        this.ProductionRef = ProductionRef;
    }

    public void createPosition(String PositionId, String PositionName, String Department, String Salary){
        this.PositionId = PositionId;
        this.PositionName = PositionName;
        this.Department = Department;
        this.Salary = Salary;
    }

    public void createProduct(String ProductName, String NumberOfPages, String ProductionRef, String ProductPrice, String ProductCirculation){
        this.ProductName = ProductName;
        this.ProductCirculation = ProductCirculation;
        this.ProductPrice = ProductPrice;
        this.NumberOfPages = NumberOfPages;
        this.ProductionRef = ProductionRef;
    }

    public void createProduction(String ProductionId, String ReleasedProducts, String DefectiveProducts, String ProductionTime){
        this.ProductionId = ProductionId;
        this.ReleasedProducts = ReleasedProducts;
        this.DefectiveProducts = DefectiveProducts;
        this.ProductionDate = ProductionTime;
    }

    public void createProductType(String ProductTypeName, String AgeLimit, String Audience, String ProductRef, String Type_id){
        this.ProductTypeName = ProductTypeName;
        this.AgeLimit = AgeLimit;
        this.Audience = Audience;
        this.Tipe_id = Type_id;
        this.ProductRef = ProductRef;
    }

    public void createProvider(String ProviderId, String ProviderName, String Resource, String ProductionRef, String ProviderPrice){
        this.ProviderId = ProviderId;
        this.ProviderName = ProviderName;
        this.ProviderPrice = ProviderPrice;
        this.Resource = Resource;
        this.ProductionRef = ProductionRef;
    }

    public void createRegion(String RegionId, String RegionName){
        this.RegionId = RegionId;
        this.RegionName = RegionName;
    }
}