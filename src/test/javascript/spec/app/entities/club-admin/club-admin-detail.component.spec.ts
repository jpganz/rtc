/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RtcprojectTestModule } from '../../../test.module';
import { Club_adminDetailComponent } from 'app/entities/club-admin/club-admin-detail.component';
import { Club_admin } from 'app/shared/model/club-admin.model';

describe('Component Tests', () => {
    describe('Club_admin Management Detail Component', () => {
        let comp: Club_adminDetailComponent;
        let fixture: ComponentFixture<Club_adminDetailComponent>;
        const route = ({ data: of({ club_admin: new Club_admin(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtcprojectTestModule],
                declarations: [Club_adminDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(Club_adminDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(Club_adminDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.club_admin).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
