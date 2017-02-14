function calcularEdad() {
    var fecha = document.getElementById('tabHC:txtFechaNacimiento_input');
    calcularEdadParam(fecha.value);
}
function calcularEdadParam(fecha) {
    if (fecha != null) {
        var fechaActual = new Date();
        var diaActual = fechaActual.getDate();
        var mmActual = fechaActual.getMonth() + 1;
        var yyyyActual = fechaActual.getFullYear();
        FechaNac = fecha.split("/");
        var diaCumple = FechaNac[0];
        var mmCumple = FechaNac[1];
        var yyyyCumple = FechaNac[2];
        var edad = yyyyActual - yyyyCumple;
        if ((mmActual < mmCumple) || (mmActual == mmCumple && diaActual < diaCumple)) {
            edad--;
        }
        alert(edad);
    }
}