import { NgModule } from '@angular/core';

import { PeSdoAmendoimSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [PeSdoAmendoimSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [PeSdoAmendoimSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class PeSdoAmendoimSharedCommonModule {}
