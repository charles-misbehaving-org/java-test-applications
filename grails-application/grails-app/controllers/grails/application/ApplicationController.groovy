package grails.application

import java.lang.management.ManagementFactory

class ApplicationController {

    static {
        if (System.getenv()['FAIL_INIT'] != null) {
            throw new RuntimeException('$FAIL_INIT caused initialisation to fail')
        }
    }

    def index() {
        if (System.getenv()['FAIL_OOM'] != null) {
            println "Exhausting heap..."
            byte[] _ = new byte[Integer.MAX_VALUE]
        }

        def runtimeMxBean = ManagementFactory.getRuntimeMXBean()
        def data = new TreeMap()
        data["Class Path"] = runtimeMxBean.classPath.split(':')
        data["Environment Variables"] = System.getenv()
        data["Input Arguments"] = runtimeMxBean.inputArguments
        data["Request Headers"] = headers

        return [data: data]
    }

    def getHeaders() {
        def data = new TreeMap()

        for(headerName in request.headerNames) {
            data[headerName] = request.getHeader(headerName)
        }

        data
    }
}
