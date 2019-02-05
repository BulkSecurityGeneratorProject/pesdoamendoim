/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PeSdoAmendoimTestModule } from '../../../test.module';
import { ConfrontationComponent } from 'app/entities/confrontation/confrontation.component';
import { ConfrontationService } from 'app/entities/confrontation/confrontation.service';
import { Confrontation } from 'app/shared/model/confrontation.model';

describe('Component Tests', () => {
    describe('Confrontation Management Component', () => {
        let comp: ConfrontationComponent;
        let fixture: ComponentFixture<ConfrontationComponent>;
        let service: ConfrontationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeSdoAmendoimTestModule],
                declarations: [ConfrontationComponent],
                providers: []
            })
                .overrideTemplate(ConfrontationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ConfrontationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfrontationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Confrontation(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.confrontations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
