/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PeSdoAmendoimTestModule } from '../../../test.module';
import { ConfrontationUpdateComponent } from 'app/entities/confrontation/confrontation-update.component';
import { ConfrontationService } from 'app/entities/confrontation/confrontation.service';
import { Confrontation } from 'app/shared/model/confrontation.model';

describe('Component Tests', () => {
    describe('Confrontation Management Update Component', () => {
        let comp: ConfrontationUpdateComponent;
        let fixture: ComponentFixture<ConfrontationUpdateComponent>;
        let service: ConfrontationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeSdoAmendoimTestModule],
                declarations: [ConfrontationUpdateComponent]
            })
                .overrideTemplate(ConfrontationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ConfrontationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfrontationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Confrontation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.confrontation = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Confrontation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.confrontation = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
