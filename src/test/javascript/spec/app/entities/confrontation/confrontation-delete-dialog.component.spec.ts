/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PeSdoAmendoimTestModule } from '../../../test.module';
import { ConfrontationDeleteDialogComponent } from 'app/entities/confrontation/confrontation-delete-dialog.component';
import { ConfrontationService } from 'app/entities/confrontation/confrontation.service';

describe('Component Tests', () => {
    describe('Confrontation Management Delete Component', () => {
        let comp: ConfrontationDeleteDialogComponent;
        let fixture: ComponentFixture<ConfrontationDeleteDialogComponent>;
        let service: ConfrontationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeSdoAmendoimTestModule],
                declarations: [ConfrontationDeleteDialogComponent]
            })
                .overrideTemplate(ConfrontationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ConfrontationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfrontationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
