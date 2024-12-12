package bancario.projeto.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

import bancario.projeto.utils.Utilitarios;

public class ContaBancaria implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer numeroConta;
    private BigDecimal saldo;
    private LocalDateTime dataAbertura;
    private boolean status;

    public ContaBancaria(Integer numeroConta) {
        this();
        this.numeroConta = numeroConta;
    }

    public ContaBancaria() {
        this.numeroConta = new Random().nextInt(999999999);
        this.saldo = BigDecimal.ZERO;
        this.dataAbertura = LocalDateTime.now();
        this.status = true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroConta);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ContaBancaria other = (ContaBancaria) obj;
        return Objects.equals(numeroConta, other.numeroConta);
    }

    public Integer getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Integer numeroConta) {
        this.numeroConta = numeroConta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "\nNumero da conta: " + numeroConta + " \n" +
                "Saldo: " + Utilitarios.formatarValor(saldo) + " \n" +
                "Data de criação: " + dataAbertura + "\n" +
                "Status: " + status + "\n";
    }

    public void depositar(BigDecimal quantia) {
        if (status) {
            if (quantia.compareTo(BigDecimal.ZERO) > 0) {
                this.saldo = this.saldo.add(quantia);
                System.out.println("\n---------------------------------------------------------------------");
                System.out.println("Depósito realizado com sucesso !");
                System.out.println("---------------------------------------------------------------------");
            } else {
                System.out.println("\n---------------------------------------------------------------------");
                System.err.println("Valor inválido para deposito !");
                System.out.println("---------------------------------------------------------------------");
            }
        } else {
            System.out.println("\n---------------------------------------------------------------------");
            System.err.println("Operação não permitida. Conta desativada !");
            System.out.println("---------------------------------------------------------------------");
        }
    }

    public void sacar(BigDecimal quantia) {
        if (status) {
            if (quantia.compareTo(BigDecimal.ZERO) > 0) {
                if (this.saldo.compareTo(quantia) >= 0) {
                    this.saldo = this.saldo.subtract(quantia);
                    System.out.println("\n---------------------------------------------------------------------");
                    System.out.println("Saque realizado com sucesso !");
                    System.out.println("---------------------------------------------------------------------");
                } else {
                    System.out.println("\n---------------------------------------------------------------------");
                    System.err.println("Saldo insuficiente !");
                    System.out.println("---------------------------------------------------------------------");
                }
            } else {
                System.out.println("\n---------------------------------------------------------------------");
                System.err.println("Valor inválido para saque !");
                System.out.println("---------------------------------------------------------------------");
            }
        } else {
            System.out.println("\n---------------------------------------------------------------------");
            System.err.println("Operação não permitida. Conta desativada !");
            System.out.println("---------------------------------------------------------------------");
        }
    }

    public void transferir(ContaBancaria c, BigDecimal quantia) {
        if (status && c.isStatus()) {
            if (quantia.compareTo(BigDecimal.ZERO) > 0) {
                if (quantia.compareTo(this.saldo) <= 0) {
                    this.saldo = this.saldo.subtract(quantia);
                    c.saldo = c.saldo.add(quantia);
                    System.out.println("\n---------------------------------------------------------------------");
                    System.out.println("Transferência realizada com sucesso !");
                    System.out.println("---------------------------------------------------------------------");
                } else {
                    System.out.println("\n---------------------------------------------------------------------");
                    System.err.println("Saldo insuficiente !");
                    System.out.println("---------------------------------------------------------------------");
                }
            } else {
                System.out.println("\n---------------------------------------------------------------------");
                System.err.println("O valor não pode ser negativo !");
                System.out.println("---------------------------------------------------------------------");
            }
        } else {
            System.out.println("\n---------------------------------------------------------------------");
            System.err.println("Operacao nao pode ser realizada entre contas desativadas !");
            System.out.println("---------------------------------------------------------------------");
        }
    }
}
