
package pe.edu.utp.sistemaimprenta.model;

import java.time.LocalDateTime;

public record Audit(User user,String description,LocalDateTime date,AuditType type) {

}
