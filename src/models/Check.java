package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Table(name = "checks")
@NamedQueries ({
    @NamedQuery(
            name = "getChecksOnReport",
            query = "SELECT c FROM Check AS c WHERE c.report = :report ORDER BY c.id DESC"
            ),
    @NamedQuery(
            name = "getReportChecksCount",
            query = "SELECT COUNT(c) FROM Check AS c WHERE c.report = :report"
            ),
    @NamedQuery(
            name = "getAllEmployeesChecks",
            query = "SELECT c FROM Check AS c WHERE c.employee = :employee ORDER BY c.id DESC "
            ),
    @NamedQuery(
            name = "getEmployeesChecksCount",
            query = "SELECT COUNT(c) FROM Check AS c WHERE c.employee = :employee"
            )

})
@Entity
public class Check {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee ;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

}
