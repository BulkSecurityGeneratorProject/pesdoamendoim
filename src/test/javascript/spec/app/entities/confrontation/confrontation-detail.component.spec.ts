/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PeSdoAmendoimTestModule } from '../../../test.module';
import { ConfrontationDetailComponent } from 'app/entities/confrontation/confrontation-detail.component';
import { Confrontation } from 'app/shared/model/confrontation.model';

describe('Component Tests', () => {
    describe('Confrontation Management Detail Component', () => {
        let comp: ConfrontationDetailComponent;
        let fixture: ComponentFixture<ConfrontationDetailComponent>;
        const route = ({ data: of({ confrontation: new Confrontation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeSdoAmendoimTestModule],
                declarations: [ConfrontationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ConfrontationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ConfrontationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.confrontation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
